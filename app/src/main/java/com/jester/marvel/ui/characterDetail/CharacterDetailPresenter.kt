package com.jester.marvel.ui.characterDetail

import com.jester.marvel.character.*
import com.jester.marvel.model.character.Character
import com.jester.marvel.ui.exception.AndroidExceptionHandler
import com.jester.marvel.ui.model.CharacterViewEntity
import com.jester.marvel.ui.model.mapper.mapToCharacter
import com.jester.marvel.ui.model.mapper.mapToCharacterViewEntity
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class CharacterDetailPresenter @Inject constructor(val view: CharacterDetailView,
                                                   val getCharacterByIdInteractor: GetCharacterByIdInteractor,
                                                   val getCharacterInfoInteractor: GetCharacterInfoInteractor,
                                                   val saveCharacterRealmInteractor: SaveFabCharacterRealmInteractor,
                                                   val removeFabCharacterInteractor: RemoveFabCharacterInteractor,
                                                   val checkIfCharacterIsFavInteractor: CheckIfCharacterIsFavInteractor,
                                                   val exceptionHandler: AndroidExceptionHandler) {

    fun onStart(id: String) {

        getCharacter(id)
    }

    private fun getCharacter(id: String) {

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
            result.success { value ->
                checkIfItsFavorite(value)
            }
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }
    }

    private fun checkIfItsFavorite(character: Character) {

        checkIfCharacterIsFavInteractor.execute(CheckIfCharacterIsFavInteractor.Parameters(character.id)) { result ->
            view.hideLoader()
            result.success { value ->
                val characterViewEntity = character.mapToCharacterViewEntity()
                characterViewEntity.isFav = true
                view.showCharacterInfo(characterViewEntity)

            }
            result.failure { exception ->
                view.showCharacterInfo(character.mapToCharacterViewEntity())
            }
        }
    }

    fun onFabButtonPressed(id: String, checked: Boolean) {

        if (checked) {
            val fabCharacterToSave = view.getFavCharacter()
            fabCharacterToSave?.let {
                saveCharacterFav(fabCharacterToSave)
            }

        } else {
            removeFabCharacter(id)

        }

    }

    private fun saveCharacterFav(fabCharacter: CharacterViewEntity) {

        saveCharacterRealmInteractor.execute(SaveFabCharacterRealmInteractor.Parameters(fabCharacter.mapToCharacter())) { result ->
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }

    }

    private fun removeFabCharacter(id: String) {

        removeFabCharacterInteractor.execute(RemoveFabCharacterInteractor.Parameters(id)) { result ->
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }

    }

    fun onShareButtonPressed() {
        view.requestPermissionToShareImage()
    }
}