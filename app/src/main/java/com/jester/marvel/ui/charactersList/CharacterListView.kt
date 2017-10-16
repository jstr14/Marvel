package com.jester.marvel.ui.charactersList

import com.jester.marvel.ui.base.BaseView
import com.jester.marvel.ui.model.CharacterViewEntity

/**
 * Created by Héctor on 10/10/2017.
 */
interface CharacterListView: BaseView {
    fun showCharacters(charactersList: List<CharacterViewEntity>)
    fun hideLoader()
    fun onCharacterPress(id: String)
}