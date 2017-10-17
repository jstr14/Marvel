package com.jester.marvel.ui.characterDetail

import dagger.Module
import dagger.Provides

/**
 * Created by Héctor on 16/10/2017.
 */
@Module
class CharacterDetailModule {

    @Provides
    internal fun provideCharacterDetailView(characterDetailActivity: CharacterDetailActivity): CharacterDetailView {
        return characterDetailActivity
    }
}