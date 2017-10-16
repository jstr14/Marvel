package com.jester.marvel.dependencyinjection.application

import android.app.Application
import android.content.Context
import com.jester.marvel.UiThread
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.data.dependencyinjection.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Borja on 21/12/16.
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(application: Application): Context {
        return application
    }


    @Provides
    @Singleton
    fun providesPostExecutionThread(): PostExecutionThread = UiThread()

}