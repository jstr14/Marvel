package com.jester.marvel.ui.favCharacterList

import com.jester.marvel.ui.base.BaseView
import com.jester.marvel.ui.model.CharacterViewEntity

/**
 * Created by Héctor on 07/11/2017.
 */
interface FavCharacterListView: BaseView {
    fun showCharacters(favCharactersList: List<CharacterViewEntity>)
    fun onCharacterPress(id: String)
    fun hideLoader()
}