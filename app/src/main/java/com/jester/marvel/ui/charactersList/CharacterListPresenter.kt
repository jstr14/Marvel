package com.jester.marvel.ui.charactersList

import com.jester.marvel.character.GetCharacterListInteractor
import com.jester.marvel.ui.exception.AndroidExceptionHandler
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class CharacterListPresenter @Inject constructor(val view: CharacterListView,
                                                 val getCharacterListInteractor: GetCharacterListInteractor,
                                                 val exceptionHandler: AndroidExceptionHandler) {


    fun onStart() {

        getCharactersListWithPagination(CharactersListActivity.INITIAL_OFFSET)
    }


    private fun getCharactersListWithPagination(offset: Int){

        getCharacterListInteractor.execute(GetCharacterListInteractor.Parameters(offset)){
            result ->
            result.success {
                value -> view.showCharacters(value)
            }
            result.failure {
                exception -> exceptionHandler.notifyException(view, exception)
            }
        }
    }
}