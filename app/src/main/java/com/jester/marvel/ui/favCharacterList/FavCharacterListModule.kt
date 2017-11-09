package com.jester.marvel.ui.favCharacterList

import dagger.Module
import dagger.Provides

/**
 * Created by Héctor on 07/11/2017.
 */
@Module
class FavCharacterListModule {

    @Provides
    internal fun provideFavCharacterListView(favCharacterListActivity: FavCharacterListActivity): FavCharacterListView {
        return favCharacterListActivity
    }
}