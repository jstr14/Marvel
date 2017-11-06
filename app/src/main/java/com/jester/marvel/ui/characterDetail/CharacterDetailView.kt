package com.jester.marvel.ui.characterDetail

import com.jester.marvel.ui.base.BaseView
import com.jester.marvel.ui.model.CharacterViewEntity

/**
 * Created by Héctor on 16/10/2017.
 */
interface CharacterDetailView: BaseView {

    fun showCharacterInfo(value: CharacterViewEntity)
    fun hideLoader()
}