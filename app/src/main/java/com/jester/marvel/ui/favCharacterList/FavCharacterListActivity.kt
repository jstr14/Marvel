package com.jester.marvel.ui.favCharacterList

import android.content.Context
import android.content.Intent
import android.view.View
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.jester.marvel.R
import com.jester.marvel.ui.Navigator
import com.jester.marvel.ui.ProgressLoader
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.favCharacterList.renderers.FavCharacterRenderer
import com.jester.marvel.ui.model.CharacterViewEntity
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.activity_fav_character_list.*
import kotlinx.android.synthetic.main.progress_loader.view.*
import javax.inject.Inject

class FavCharacterListActivity : BaseActivity(), FavCharacterListView {


    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, FavCharacterListActivity::class.java)
        }
    }


    @Inject lateinit var characterRenderer: FavCharacterRenderer
    lateinit var adapter: RendererAdapter<Any>
    @Inject lateinit var progressLoader: ProgressLoader
    @Inject lateinit var presenter: FavCharacterListPresenter
    @Inject lateinit var navigator: Navigator

    override fun onRequestLayout(): Int {
        return R.layout.activity_fav_character_list
    }

    override fun onViewLoaded() {

        setActionBar()
        progressLoader.addImagesToProgressLoader(loading.loading_view, this)
        setRecyclerView()
        presenter.onStart()
    }

    private fun setActionBar() {

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }


    private fun setRecyclerView() {

        val carouselLayoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        carouselLayoutManager.maxVisibleItems = 2
        recyclerView.layoutManager = carouselLayoutManager
        recyclerView.addOnScrollListener(CenterScrollListener())

        adapter = RendererBuilder.create<Any>()
                .bind(CharacterViewEntity::class.java, characterRenderer)
                .build()
                .into(recyclerView)

    }

    override fun showCharacters(charactersList: List<CharacterViewEntity>) {

        if(charactersList.isEmpty()){
            showEmptyMessage()
        } else {
            adapter.addAll(charactersList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showEmptyMessage(){

        recyclerView.visibility = View.GONE
        emptyListMessage.visibility = View.VISIBLE
    }

    override fun onCharacterPress(id: String) {

        navigator.navigateToCharacterDetailActivity(this, id)
    }


    override fun hideLoader() {
        loading.visibility = View.GONE
        progressLoader.pauseAnimation(loading.loading_view)
    }
}
