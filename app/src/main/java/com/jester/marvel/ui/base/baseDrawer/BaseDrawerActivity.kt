package com.jester.marvel.ui.base.baseDrawer

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jester.marvel.R
import com.jester.marvel.ui.Navigator
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.design.longSnackbar
import javax.inject.Inject

/**
 * Created by Héctor on 07/11/2017.
 */
abstract class BaseDrawerActivity : AppCompatActivity(), BaseDrawerView {


    @Inject lateinit var drawerPresenter: BaseDrawerPresenter
    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        replaceFrameView()
        setActionBar()
        setNavigationDrawer()
        onViewLoaded()
    }

    private fun replaceFrameView(){

        layoutInflater.inflate(onRequestLayout(),content_frame)
    }

    abstract fun onRequestLayout(): Int

    private fun setNavigationDrawer(){

        navview.setNavigationItemSelectedListener({ menuItem ->

            when (menuItem.itemId) {
                R.id.favOption -> {
                    drawerPresenter.onFavOptionPressed()
                }

                R.id.comicsList -> {
                    drawerPresenter.onComicsListPressed()
                }
            }
            true
        })
    }

    private fun setActionBar() {

        setSupportActionBar(toolbarDrawer.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.burger_icon)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerPresenter.onHomeButtonPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openOrCloseNavigationDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
        } else drawer_layout.openDrawer(GravityCompat.START)
    }

    override fun navigateToComicsList() {
        navigator.navigateToComicsList(this)
    }

    override fun navigateToFavCharacters() {
       navigator.navigateToFavCharacters(this)
    }

    override fun showException(exceptionMessage: String) {

        longSnackbar(findViewById(android.R.id.content),exceptionMessage)
    }

    abstract fun onViewLoaded()
}