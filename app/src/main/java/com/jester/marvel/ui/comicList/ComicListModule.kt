package com.jester.marvel.ui.comicList

import dagger.Module
import dagger.Provides

/**
 * Created by Héctor on 22/11/2017.
 */
@Module
class ComicListModule {

    @Provides
    internal fun providesComicListView(comicListActivity: ComicListActivity): ComicListView {
        return comicListActivity
    }
}