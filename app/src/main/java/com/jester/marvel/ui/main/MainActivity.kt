package com.jester.marvel.ui.main

import com.jester.marvel.R
import com.jester.marvel.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject lateinit var presenter: MainPresenter

    override fun onRequestLayout(): Int = R.layout.activity_main


    override fun onViewLoaded() {
        presenter.pastel()
    }


}
