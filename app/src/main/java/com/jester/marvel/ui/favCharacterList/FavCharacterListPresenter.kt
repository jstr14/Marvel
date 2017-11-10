package com.jester.marvel.ui.favCharacterList

import com.jester.marvel.character.GetFavCharactersInteractor
import com.jester.marvel.ui.exception.AndroidExceptionHandler
import com.jester.marvel.ui.model.mapper.mapToCharacterViewEntity
import javax.inject.Inject

/**
 * Created by Héctor on 07/11/2017.
 */
class FavCharacterListPresenter @Inject constructor(val view: FavCharacterListView,
                                                    val getFavCharactersInteractor: GetFavCharactersInteractor,
                                                    val exceptionHandler: AndroidExceptionHandler) {

    fun onStart() {

        getFavCharacters()
    }

    private fun getFavCharacters() {

        getFavCharactersInteractor.execute(Unit) { result ->
            result.success { value ->
                view.hideLoader()
                view.showCharacters(value.map { it.mapToCharacterViewEntity() })
            }
            result.failure { exception ->
                view.hideLoader()
                exceptionHandler.notifyException(view, exception)

            }
        }
    }

    fun onCharacterPressed(id: String) {
        view.onCharacterPress(id)
    }
}