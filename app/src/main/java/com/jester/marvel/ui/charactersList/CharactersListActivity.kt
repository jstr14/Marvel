package com.jester.marvel.ui.charactersList

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
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


class CharactersListActivity : BaseDrawerActivity(), CharacterListView, SearchView.OnQueryTextListener {


    @Inject lateinit var presenter: CharacterListPresenter
    @Inject lateinit var progressLoader: ProgressLoader
    @Inject lateinit var characterRenderer: CharacterRenderer
    @Inject lateinit var footerRenderer: FooterRenderer
    lateinit var searchView: SearchView
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchMenuItem = menu.findItem(R.id.action_search)
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                presenter.onCloseSearchView()
                return true
            }
        })
        searchView = searchMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {

        presenter.onSearchQueryChange(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(root.applicationWindowToken, 0)
        return true
    }

    override fun hideLoader() {
        loading.visibility = View.GONE
        progressLoader.pauseAnimation(loading.loading_view)

    }

    override fun showProgressLoader() {
        progress_loading.visibility = View.VISIBLE
    }

    override fun hideProgressLoader() {
        progress_loading.visibility = View.GONE
    }

    override fun showCharacters(charactersList: List<CharacterViewEntity>) {

        removeProgressBarFromRecyclerView()
        adapter.addAll(charactersList)
        adapter.notifyDataSetChanged()
    }

    override fun showQueryCharacters(charactersList: List<CharacterViewEntity>,isFirstQuery: Boolean) {

        if(isFirstQuery) adapter.clearAndNotify()
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

    override fun getQueryName(): String {

        return searchView.query.toString()
    }

    override fun clearRecyclerList() {
        adapter.clearAndNotify()
    }
}
