package com.jester.marvel.ui.charactersList

import com.jester.marvel.character.*
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
                                                 val queryByNameCharacterListInteractor: QueryByNameCharacterListInteractor,
                                                 val getFavCharactersInteractor: GetFavCharactersInteractor,
                                                 val saveCharacterRealmInteractor: SaveFabCharacterRealmInteractor,
                                                 val removeFabCharacterInteractor: RemoveFabCharacterInteractor,
                                                 val exceptionHandler: AndroidExceptionHandler) {

    var favList = listOf<String>()
    var search = false

    fun onStart() {

        getFavCharacters()
    }

    private fun getFavCharacters() {

        getFavCharactersInteractor.execute(Unit) { result ->
            result.success { value ->
                favList = value.map { it.id }
                getCharactersListWithPagination(CharactersListActivity.INITIAL_OFFSET)
            }
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)
            }
        }
    }




    private fun getCharactersListWithPagination(offset: Int) {

        getCharacterListInteractor.execute(GetCharacterListInteractor.Parameters(offset)) { result ->
            result.success { value ->
                view.hideLoader()
                view.hideProgressLoader()
                view.showCharacters(markAsFav(value))

            }
            result.failure { exception ->
                view.hideLoader()
                view.hideProgressLoader()
                exceptionHandler.notifyException(view, exception)
            }
        }
    }

    private fun queryCharacterList(offset: Int, queryName: String,isFirstQuery:Boolean) {

        view.showProgressLoader()
        queryByNameCharacterListInteractor.execute(QueryByNameCharacterListInteractor.Parameters(offset,queryName)){ result ->
            result.success { value ->
                view.hideProgressLoader()
                view.showQueryCharacters(markAsFav(value),isFirstQuery)

            }
            result.failure { exception ->
                view.hideProgressLoader()
                exceptionHandler.notifyException(view, exception)
            }
        }

    }

    private fun markAsFav(characterList: List<Character>): List<CharacterViewEntity> {

        val viewList = characterList.map(Character::mapToCharacterViewEntity)

        viewList
                .filter { favList.contains(it.id) }
                .forEach { it.isFav = true }

        return viewList


    }

    fun showMoreCharacter(offset: Int) {

        if(search){

            queryCharacterList(offset, view.getQueryName(),false)
        }

        else getCharactersListWithPagination(offset)
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

        view.updateIsFavButton(id, checked)

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

    fun onSearchQueryChange(queryName: String) {


        if (queryName.length >= 3) {
            search = true
            queryCharacterList(CharactersListActivity.INITIAL_OFFSET, queryName,true)
        }
    }

    fun onCloseSearchView() {

        if(search){
            view.showProgressLoader()
            view.clearRecyclerList()
            onStart()
        }
        search = false

    }

}