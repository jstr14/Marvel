package com.jester.marvel.ui.charactersList

import com.jester.marvel.character.GetCharacterListInteractor
import com.jester.marvel.character.RemoveFabCharacterInteractor
import com.jester.marvel.character.SaveFabCharacterRealmInteractor
import com.jester.marvel.model.character.Character
import com.jester.marvel.ui.exception.AndroidExceptionHandler
import com.jester.marvel.ui.model.CharacterViewEntity
import com.jester.marvel.ui.model.mapper.mapToCharacter
import com.jester.marvel.ui.model.mapper.mapToCharacterViewEntity
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class CharacterListPresenter @Inject constructor(val view: CharacterListView,
                                                 val getCharacterListInteractor: GetCharacterListInteractor,
                                                 val saveCharacterRealmInteractor: SaveFabCharacterRealmInteractor,
                                                 val removeFabCharacterInteractor: RemoveFabCharacterInteractor,
                                                 val exceptionHandler: AndroidExceptionHandler) {


    fun onStart() {

        getCharactersListWithPagination(CharactersListActivity.INITIAL_OFFSET)
    }


    private fun getCharactersListWithPagination(offset: Int) {

        getCharacterListInteractor.execute(GetCharacterListInteractor.Parameters(offset)) { result ->
            result.success { value ->
                view.hideLoader()
                view.showCharacters(value.map(Character::mapToCharacterViewEntity))

            }
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }
    }

    fun showMoreCharacter(offset: Int) {

        getCharactersListWithPagination(offset)
    }


    fun onCharacterPressed(id: String) {
        view.onCharacterPress(id)
    }

    fun onFabButtonPressed(id: String, checked: Boolean) {

        if(checked){
            val fabCharacterToSave = view.getSelectedFavCharacterFromId(id)
            fabCharacterToSave?.let {
                saveCharacterFav(fabCharacterToSave)
            }

        } else{
            removeFabCharacter(id)

        }

    }

    private fun saveCharacterFav(fabCharacter: CharacterViewEntity){

        saveCharacterRealmInteractor.execute(SaveFabCharacterRealmInteractor.Parameters(fabCharacter.mapToCharacter())){
            result ->
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }

    }

    private fun removeFabCharacter(id: String){

        removeFabCharacterInteractor.execute(RemoveFabCharacterInteractor.Parameters(id)){
            result ->
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }

    }
}