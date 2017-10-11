package com.jester.marvel.ui.renderers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jester.marvel.R
import com.pedrogomez.renderers.Renderer
import kotlinx.android.synthetic.main.section_renderer.view.*

/**
 * Created by Héctor on 11/10/2017.
 */
open class SectionRenderer : Renderer<String>() {


    override fun inflate(inflater: LayoutInflater, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.section_renderer, parent, false)

    }

    override fun render(p0: MutableList<Any>?) {

        this.rootView.title.text = content
    }
}