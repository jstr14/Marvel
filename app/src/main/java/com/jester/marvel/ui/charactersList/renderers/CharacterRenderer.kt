package com.jester.marvel.ui.charactersList.renderers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jester.marvel.R
import com.jester.marvel.data.repository.character.model.ImageViewEntity
import com.jester.marvel.ui.charactersList.CharacterListPresenter
import com.jester.marvel.ui.load
import com.jester.marvel.ui.model.CharacterViewEntity
import com.pedrogomez.renderers.Renderer
import kotlinx.android.synthetic.main.character_item.view.*
import javax.inject.Inject


/**
 * Created by Héctor on 11/10/2017.
 */
class CharacterRenderer @Inject constructor(val presenter: CharacterListPresenter): Renderer<CharacterViewEntity>() {

    override fun render(p0: MutableList<Any>?) {

        val character = content
        renderName(character.name)
        renderImage(character.image)

        this.rootView.setOnClickListener{

            presenter.onCharacterPressed(character.id)
        }

        this.rootView.fabButton.setOnClickListener{

            presenter.onFabButtonPressed(character.id, this.rootView.fabButton.isChecked)
        }
    }

    override fun inflate(inflater: LayoutInflater, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.character_item, parent, false)
    }


    private fun renderName(name: String) {
        this.rootView.name.text = name
    }

    private fun renderImage(image: ImageViewEntity){

        val properPath = image.path+"/"+this.context.getString(R.string.square)+"."+image.extension
        this.rootView.characterImage.load(properPath)

    }



}