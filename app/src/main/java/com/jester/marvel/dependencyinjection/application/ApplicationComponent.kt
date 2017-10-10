package com.jester.marvel.dependencyinjection.application

import android.app.Application
import com.jester.marvel.BaseApplication
import com.jester.marvel.data.dependencyinjection.DataModule
import com.jester.marvel.dependencyinjection.activity.ActivityInjector
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Borja on 21/12/16.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, AndroidInjectionModule::class,
        ActivityInjector::class, DataModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}