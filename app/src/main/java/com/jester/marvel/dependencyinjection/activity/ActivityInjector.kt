package com.jester.marvel.dependencyinjection.activity

import com.jester.marvel.ui.characterDetail.CharacterDetailActivity
import com.jester.marvel.ui.characterDetail.CharacterDetailModule
import com.jester.marvel.ui.charactersList.CharacterListModule
import com.jester.marvel.ui.charactersList.CharactersListActivity
import com.jester.marvel.ui.favCharacterList.FavCharacterListActivity
import com.jester.marvel.ui.favCharacterList.FavCharacterListModule
import com.jester.marvel.ui.main.MainActivity
import com.jester.marvel.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Borja on 17/7/17.
 */

@Module
abstract class ActivityInjector {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeMainActivityInjector(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(CharacterListModule::class))
    abstract fun constributeCharacterListInjector(): CharactersListActivity

    @ContributesAndroidInjector(modules = arrayOf(CharacterDetailModule::class))
    abstract fun contributeCharacterDetailInjector(): CharacterDetailActivity

    @ContributesAndroidInjector(modules = arrayOf(FavCharacterListModule::class))
    abstract fun contributeFavCharacterListInjector(): FavCharacterListActivity

}