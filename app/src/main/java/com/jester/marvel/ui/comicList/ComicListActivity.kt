package com.jester.marvel.ui.comicList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.jester.marvel.R
import com.jester.marvel.ui.VerticalSpaceItemDecorator
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.model.ComicViewEntity
import kotlinx.android.synthetic.main.activity_comic_list.*
import kotlinx.android.synthetic.main.character_list.*
import javax.inject.Inject

class ComicListActivity : BaseActivity(), ComicListView, SearchView.OnQueryTextListener {

    companion object {

        const val ITEM_SPACE = 4
        const val FIRST_ITEM_SPACE = 16
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, ComicListActivity::class.java)
        }
    }

    lateinit var searchView: SearchView
    @Inject lateinit var presenter: ComicListPresenter
    @Inject lateinit var adapter: ComicListAdapter


    override fun onRequestLayout(): Int {
        return R.layout.activity_comic_list
    }

    override fun onViewLoaded() {

        setSupportBar()
        val layoutManager = LinearLayoutManager(this)
        comicList.layoutManager = layoutManager
        comicList.adapter = adapter
        comicList.addItemDecoration(VerticalSpaceItemDecorator(ITEM_SPACE, this,true, FIRST_ITEM_SPACE))

        presenter.onStart()

    }

    private fun setSupportBar(){
        supportActionBar?.apply {
            title = "Comics List"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(root.applicationWindowToken, 0)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        presenter.onSearchQueryChange(newText)
        return true
    }

    override fun showComics(comicList: List<ComicViewEntity>) {

        adapter.comicList = comicList
    }
}
