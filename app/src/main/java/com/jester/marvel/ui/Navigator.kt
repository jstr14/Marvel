package com.jester.marvel.ui

import android.content.Context
import com.jester.marvel.ui.characterDetail.CharacterDetailActivity
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class Navigator @Inject constructor() {

    fun navigateToCharacterDetailActivity(context: Context, characterId: String){

        val intent = CharacterDetailActivity.getIntent(context)
        intent.putExtra(CharacterDetailActivity.ID, characterId)
        context.startActivity(intent)
    }
}