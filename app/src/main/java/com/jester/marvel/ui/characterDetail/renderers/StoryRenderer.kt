package com.jester.marvel.ui.characterDetail.renderers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jester.marvel.R
import com.jester.marvel.data.repository.character.model.ImageViewEntity
import com.jester.marvel.ui.load
import com.jester.marvel.ui.model.StoryViewEntity
import com.pedrogomez.renderers.Renderer
import kotlinx.android.synthetic.main.comic_item.view.*
import javax.inject.Inject

/**
 * Created by Héctor on 23/10/2017.
 */
class StoryRenderer @Inject constructor(): Renderer<StoryViewEntity>() {

    override fun render(p0: MutableList<Any>?) {

        val story = content
        renderImage(story.image)

    }

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.comic_item, parent, false)
    }

    private fun renderImage(image: ImageViewEntity?){

        val properPath = image?.path+"/"+this.context.getString(R.string.portrait)+"."+image?.extension
        this.rootView.comicImage.load(properPath)

    }



}