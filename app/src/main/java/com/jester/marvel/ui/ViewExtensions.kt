package com.jester.marvel.ui

import android.widget.ImageView
import com.jester.marvel.R
import com.jester.marvel.dependencyinjection.application.GlideApp

/**
 * Created by Héctor on 13/10/2017.
 */
fun ImageView.load(url: String) {

    GlideApp.with(this)
            .load(url)
            .error(R.color.grey)
            .placeholder(R.color.grey)
            .into(this)
}