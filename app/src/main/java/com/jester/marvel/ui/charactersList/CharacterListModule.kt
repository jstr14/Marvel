package com.jester.marvel.ui.charactersList

import com.jester.marvel.ui.base.baseDrawer.BaseDrawerView
import dagger.Module
import dagger.Provides

/**
 * Created by Héctor on 10/10/2017.
 */
@Module
class CharacterListModule {

    @Provides
    internal fun provideCharacterListView(charactersListActivity: CharactersListActivityBase): CharacterListView {
        return charactersListActivity
    }

    @Provides
    internal fun provideCharacterListView2(charactersListActivity: CharactersListActivityBase): BaseDrawerView {
        return charactersListActivity
    }
}