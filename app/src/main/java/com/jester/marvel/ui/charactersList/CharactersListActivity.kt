package com.jester.marvel.ui.charactersList

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jester.marvel.R
import com.jester.marvel.ui.ProgressLoader
import com.jester.marvel.ui.base.baseDrawer.BaseDrawerActivity
import com.jester.marvel.ui.charactersList.renderers.CharacterRenderer
import com.jester.marvel.ui.charactersList.renderers.FooterRenderer
import com.jester.marvel.ui.model.CharacterViewEntity
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.character_list.*
import kotlinx.android.synthetic.main.progress_loader.view.*
import javax.inject.Inject


class CharactersListActivity : BaseDrawerActivity(), CharacterListView {


    @Inject lateinit var presenter: CharacterListPresenter
    @Inject lateinit var progressLoader: ProgressLoader
    @Inject lateinit var characterRenderer: CharacterRenderer
    @Inject lateinit var footerRenderer: FooterRenderer
    var listIdFromFavCharacters = arrayListOf<String>()
    lateinit var adapter: RendererAdapter<Any>

    companion object {
        const val INITIAL_OFFSET = 0
        var progressVisible = false
        var retrievingCharacters = false
        var hasMore = true
        val FOOTER = "Footer"
        val COLUMN_NUMBER = 2
        var SPAN_FULL = COLUMN_NUMBER
        var SPAN_SIZE_BASE = 1

    }

    override fun onRequestLayout(): Int {
        return R.layout.character_list
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

        removeProgressBarFromRecyclerView()
        adapter.addAll(charactersList)
        adapter.notifyDataSetChanged()
    }

    private fun setRecyclerView() {

        val gridLayoutManager = GridLayoutManager(this, COLUMN_NUMBER)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItem(position) is String) SPAN_FULL else SPAN_SIZE_BASE
            }
        }
        recyclerView.layoutManager = gridLayoutManager

        adapter = RendererBuilder.create<Any>()
                .bind(CharacterViewEntity::class.java, characterRenderer)
                .bind(String::class.java, footerRenderer)
                .build()
                .into(recyclerView)

        setScrollListener()
    }

    private fun setScrollListener() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (!progressVisible && hasMore) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    if (layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.itemCount - 4 && !retrievingCharacters) {

                        showProgressBarOnRecyclerView()
                        retrievingCharacters = true
                        presenter.showMoreCharacter(adapter.collection.size)

                    }
                }
            }
        })
    }

    private fun showProgressBarOnRecyclerView() {

        adapter.add(FOOTER)
        adapter.notifyDataSetChanged()
        //recyclerView.scrollToPosition(adapter.itemCount - 1)
        progressVisible = true
    }

    private fun removeProgressBarFromRecyclerView() {

        if (adapter.collection.isNotEmpty()) {
            adapter.remove(adapter.getItem(adapter.collection.size - 1))
            progressVisible = false
            retrievingCharacters = false
        }
    }

    override fun onCharacterPress(id: String) {

        navigator.navigateToCharacterDetailActivity(this, id)
    }

    override fun getSelectedFavCharacterFromId(id: String): CharacterViewEntity? {

        return (adapter.collection as List<CharacterViewEntity>).find { it.id == id }
    }

    override fun updateIsFavButton(id: String, checked: Boolean) {

        (adapter.collection as List<CharacterViewEntity>).find { it.id == id }?.isFav = checked
    }
}
