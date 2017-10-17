package com.jester.marvel.ui.characterDetail

import android.content.Context
import android.content.Intent
import com.jester.marvel.R
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.model.CharacterViewEntity
import org.jetbrains.anko.longToast
import javax.inject.Inject

class CharacterDetailActivity : BaseActivity(), CharacterDetailView {


    companion object {

        const val ID = "ID"
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, CharacterDetailActivity::class.java)
        }
    }

    @Inject lateinit var presenter: CharacterDetailPresenter

    override fun onRequestLayout(): Int {
        return R.layout.activity_character_detail
    }

    override fun onViewLoaded() {

        val characterID = intent.extras.getString(ID)

        presenter.onStart(characterID)
    }

    override fun showCharacterInfo(characterViewEntity: CharacterViewEntity) {

        longToast(characterViewEntity.id)

    }
}
