package com.jester.marvel.ui.charactersList.renderers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jester.marvel.R
import com.pedrogomez.renderers.Renderer

/**
 * Created by Héctor on 11/10/2017.
 */
class FooterRenderer : Renderer<String>() {

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.progress_dialog, parent, false)
    }

    override fun render(p0: MutableList<Any>?) {
        content
    }
}