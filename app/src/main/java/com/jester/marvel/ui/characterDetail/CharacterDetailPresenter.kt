package com.jester.marvel.ui.characterDetail

import com.jester.marvel.character.GetCharacterInfoInteractor
import com.jester.marvel.model.character.Character
import com.jester.marvel.model.image.Image
import com.jester.marvel.ui.exception.AndroidExceptionHandler
import com.jester.marvel.ui.model.mapper.mapToCharacterViewEntity
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class CharacterDetailPresenter @Inject constructor(val view: CharacterDetailView,
                                                   val getCharacterInfoInteractor: GetCharacterInfoInteractor,
                                                   val exceptionHandler: AndroidExceptionHandler) {

    fun onStart(id: String) {

        getCharacterInfo(id)
    }

    private fun getCharacterInfo(id: String) {

        getCharacterInfoInteractor.execute(GetCharacterInfoInteractor.Parameters(Character(id, "", Image("", ""), listOf(), listOf(), listOf()))) { result ->
            result.success { value ->

                view.showCharacterInfo(value.mapToCharacterViewEntity())

            }
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }
    }
}