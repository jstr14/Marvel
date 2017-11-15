package com.jester.marvel.ui.charactersList

import com.jester.marvel.ui.base.baseDrawer.BaseDrawerView
import com.jester.marvel.ui.model.CharacterViewEntity

/**
 * Created by Héctor on 10/10/2017.
 */
interface CharacterListView: BaseDrawerView {
    fun showCharacters(charactersList: List<CharacterViewEntity>)
    fun showQueryCharacters(markAsFav: List<CharacterViewEntity>,isFirstQuery: Boolean)
    fun hideLoader()
    fun showProgressLoader()
    fun hideProgressLoader()
    fun onCharacterPress(id: String)
    fun getSelectedFavCharacterFromId(id: String): CharacterViewEntity?
    fun updateIsFavButton(id: String, checked: Boolean)
    fun getQueryName(): String
    fun clearRecyclerList()
}