package com.jester.marvel.ui.base.baseDrawer

import com.jester.marvel.ui.base.BaseView

/**
 * Created by Héctor on 07/11/2017.
 */
interface BaseDrawerView : BaseView {
    fun navigateToFavCharacters()
    fun openOrCloseNavigationDrawer()
    fun navigateToComicsList()
}