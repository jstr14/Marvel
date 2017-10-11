package com.jester.marvel.ui.charactersList

import android.view.View
import com.jester.marvel.R
import com.jester.marvel.model.Character
import com.jester.marvel.ui.ProgressLoader
import com.jester.marvel.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_characters_list.*
import kotlinx.android.synthetic.main.progress_loader.view.*
import org.jetbrains.anko.longToast
import javax.inject.Inject

class CharactersListActivity : BaseActivity(), CharacterListView {


    companion object {

        const val INITIAL_OFFSET = 0
    }

    @Inject lateinit var presenter: CharacterListPresenter
    @Inject lateinit var progressLoader: ProgressLoader

    override fun onRequestLayout(): Int {
        return R.layout.activity_characters_list
    }

    override fun onViewLoaded() {

        progressLoader.addImagesToProgressLoader(loading.loading_view,this)

        presenter.onStart()


    }

    override fun hideLoader() {
        loading.visibility = View.GONE
        progressLoader.pauseAnimation(loading.loading_view)

    }

    override fun showCharacters(charactersList: List<Character>) {
        longToast("Hay personajes para mostrar")
    }
}
