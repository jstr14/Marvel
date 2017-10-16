package com.jester.marvel.ui.charactersList

import dagger.Module
import dagger.Provides

/**
 * Created by Héctor on 10/10/2017.
 */
@Module
class CharacterListModule {

    @Provides
    internal fun provideCharacterListView(charactersListActivity: CharactersListActivity): CharacterListView {
        return charactersListActivity
    }
}