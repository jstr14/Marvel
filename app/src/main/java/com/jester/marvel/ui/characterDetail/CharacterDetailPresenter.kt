package com.jester.marvel.ui.characterDetail

import com.jester.marvel.character.GetCharacterByIdInteractor
import com.jester.marvel.character.GetCharacterInfoInteractor
import com.jester.marvel.model.character.Character
import com.jester.marvel.ui.exception.AndroidExceptionHandler
import com.jester.marvel.ui.model.mapper.mapToCharacterViewEntity
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class CharacterDetailPresenter @Inject constructor(val view: CharacterDetailView,
                                                   val getCharacterByIdInteractor: GetCharacterByIdInteractor,
                                                   val getCharacterInfoInteractor: GetCharacterInfoInteractor,
                                                   val exceptionHandler: AndroidExceptionHandler) {

    fun onStart(id: String) {

        getCharacter(id)
    }

    private fun getCharacter(id: String){

        getCharacterByIdInteractor.execute(GetCharacterByIdInteractor.Parameters(id)) { result ->
           result.success { value ->
               getCharacterInfo(value)

           }
           result.failure { exception ->
               exceptionHandler.notifyException(view, exception)
           }
       }

    }

    private fun getCharacterInfo(character: Character) {

        getCharacterInfoInteractor.execute(GetCharacterInfoInteractor.Parameters(character)) { result ->
            view.hideLoader()
            result.success { value ->
                view.showCharacterInfo(value.mapToCharacterViewEntity())

            }
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }
    }
}