package com.jester.marvel.ui.comicList

import com.jester.marvel.ui.base.BaseView
import com.jester.marvel.ui.model.ComicViewEntity

/**
 * Created by Héctor on 22/11/2017.
 */
interface ComicListView: BaseView {

    fun showComics(map: List<ComicViewEntity>)
}