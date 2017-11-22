package com.jester.marvel.ui.comicList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.jester.marvel.R
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.model.ComicViewEntity
import kotlinx.android.synthetic.main.character_list.*
import javax.inject.Inject

class ComicListActivity : BaseActivity(), ComicListView, SearchView.OnQueryTextListener {

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, ComicListActivity::class.java)
        }
    }

    lateinit var searchView: SearchView
    @Inject lateinit var presenter: ComicListPresenter


    override fun onRequestLayout(): Int {
        return R.layout.activity_comic_list
    }

    override fun onViewLoaded() {

        setSupportBar()

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

    override fun showComics(map: List<ComicViewEntity>) {

        //TODO Show comics info
    }
}
