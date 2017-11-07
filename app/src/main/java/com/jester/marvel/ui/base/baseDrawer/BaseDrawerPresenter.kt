package com.jester.marvel.ui.base.baseDrawer

import javax.inject.Inject

/**
 * Created by Héctor on 07/11/2017.
 */
class BaseDrawerPresenter @Inject constructor(val drawerView: BaseDrawerView){

    fun onFavOptionPressed() {
        drawerView.navigateToFavCharacters()
    }

    fun onHomeButtonPressed() {
        drawerView.openOrCloseNavigationDrawer()
    }
}