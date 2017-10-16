package com.jester.marvel.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import org.jetbrains.anko.design.longSnackbar

/**
 * Created by Borja on 21/12/16.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(onRequestLayout())
        onViewLoaded()
    }

    abstract fun onRequestLayout(): Int


    abstract fun onViewLoaded()

    override fun showException(exceptionMessage: String) {

        longSnackbar(findViewById(android.R.id.content),exceptionMessage)
    }

}
