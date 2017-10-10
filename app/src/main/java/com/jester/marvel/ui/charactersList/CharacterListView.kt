package com.jester.marvel.ui.charactersList

import com.jester.marvel.model.Character
import com.jester.marvel.ui.base.BaseView

/**
 * Created by Héctor on 10/10/2017.
 */
interface CharacterListView: BaseView {
    fun showCharacters(charactersList: List<Character>)
}