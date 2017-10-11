package com.jester.marvel.ui.charactersList

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jester.marvel.R
import com.jester.marvel.ui.ProgressLoader
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.charactersList.renderers.CharacterRenderer
import com.jester.marvel.ui.charactersList.renderers.FooterRenderer
import com.jester.marvel.ui.model.CharacterViewEntity
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.activity_characters_list.*
import kotlinx.android.synthetic.main.progress_loader.view.*
import javax.inject.Inject


class CharactersListActivity : BaseActivity(), CharacterListView {


    companion object {
        const val INITIAL_OFFSET = 0
        var progressVisible = false
        var retrievingCharacters = false
        var hasMore = true
        var FOOTER = "Footer"
    }

    @Inject lateinit var presenter: CharacterListPresenter
    @Inject lateinit var progressLoader: ProgressLoader
    lateinit var adapter: RendererAdapter<Any>

    override fun onRequestLayout(): Int {
        return R.layout.activity_characters_list
    }

    override fun onViewLoaded() {

        progressLoader.addImagesToProgressLoader(loading.loading_view, this)
        setRecyclerView()
        presenter.onStart()


    }


    override fun hideLoader() {
        loading.visibility = View.GONE
        progressLoader.pauseAnimation(loading.loading_view)

    }

    override fun showCharacters(charactersList: List<CharacterViewEntity>) {

        if(adapter.collection.isNotEmpty()){
            adapter.remove(adapter.getItem(adapter.collection.size-1))
            progressVisible = false
            retrievingCharacters = false
        }

        adapter.addAll(charactersList)
        adapter.notifyDataSetChanged()
    }

    private fun setRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RendererBuilder.create<Any>()
                .bind(CharacterViewEntity::class.java, CharacterRenderer())
                .bind(String::class.java, FooterRenderer())
                .build()
                .into(recyclerView)

        setScrollListener()
    }

    private fun setScrollListener() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                dx.toInt()
                if (!progressVisible && hasMore) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    //position starts at 0
                    if (layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.itemCount - 2 && !retrievingCharacters) {
                        adapter.add(FOOTER)
                        adapter.notifyDataSetChanged()
                        progressVisible = true


                        //TODO call presenter with offset
                        retrievingCharacters = true
                        presenter.showMoreCharacter(adapter.collection.size)

                    }
                }
            }
        })
    }
}
