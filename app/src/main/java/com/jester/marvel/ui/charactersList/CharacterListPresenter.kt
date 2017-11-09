package com.jester.marvel.ui.charactersList

import com.jester.marvel.character.GetCharacterListInteractor
import com.jester.marvel.character.GetFavCharactersInteractor
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
                                                 val getFavCharactersInteractor: GetFavCharactersInteractor,
                                                 val saveCharacterRealmInteractor: SaveFabCharacterRealmInteractor,
                                                 val removeFabCharacterInteractor: RemoveFabCharacterInteractor,
                                                 val exceptionHandler: AndroidExceptionHandler) {

    var favList = listOf<String>()

    fun onStart() {

        getFavCharacters()
    }

    private fun getFavCharacters() {

        getFavCharactersInteractor.execute(Unit) { result ->
            result.success { value ->
                favList = value.map { it.id }
                getCharactersListWithPagination(CharactersListActivity.INITIAL_OFFSET)
            }
            result.failure {exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }
    }


    private fun getCharactersListWithPagination(offset: Int) {

        getCharacterListInteractor.execute(GetCharacterListInteractor.Parameters(offset)) { result ->
            result.success { value ->
                view.hideLoader()
                view.showCharacters(markAsFav(value))

            }
            result.failure { exception ->
                view.hideLoader()
                exceptionHandler.notifyException(view, exception)
            }
        }
    }

    private fun markAsFav(characterList: List<Character>): List<CharacterViewEntity> {

        val viewList = characterList.map(Character::mapToCharacterViewEntity)

        for (characterViewEntity in viewList) {
            if(favList.contains(characterViewEntity.id)) characterViewEntity.isFav = true
        }

        return viewList


    }

    fun showMoreCharacter(offset: Int) {

        getCharactersListWithPagination(offset)
    }


    fun onCharacterPressed(id: String) {
        view.onCharacterPress(id)
    }

    fun onFabButtonPressed(id: String, checked: Boolean) {

        if (checked) {
            val fabCharacterToSave = view.getSelectedFavCharacterFromId(id)
            fabCharacterToSave?.let {
                saveCharacterFav(fabCharacterToSave)
            }

        } else {
            removeFabCharacter(id)

        }

        view.updateIsFavButton(id,checked)

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

}