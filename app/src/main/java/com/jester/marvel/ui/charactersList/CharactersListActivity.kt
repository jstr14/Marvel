package com.jester.marvel.ui.charactersList

import com.jester.marvel.R
import com.jester.marvel.model.Character
import com.jester.marvel.ui.base.BaseActivity
import org.jetbrains.anko.longToast
import javax.inject.Inject

class CharactersListActivity : BaseActivity(), CharacterListView {


    companion object {

        const val INITIAL_OFFSET = 0
    }

    @Inject lateinit var presenter: CharacterListPresenter

    override fun onRequestLayout(): Int {
        return R.layout.activity_characters_list
    }

    override fun onViewLoaded() {

        presenter.onStart()
    }

    override fun showCharacters(charactersList: List<Character>) {
        longToast("Hay personajes para mostrar")
    }
}
