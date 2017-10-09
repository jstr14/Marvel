package com.jester.marvel.data.dependencyinjection

import com.jester.marvel.data.repository.query.Query
import com.jester.marvel.data.dependencyinjection.qualifier.DefaultQueries
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import javax.inject.Singleton

/**
 * Created by Borja on 4/1/17.
 */
@Module
class DataModule {

    @Provides
    @ElementsIntoSet
    @Singleton
    @DefaultQueries
    fun provideDefaultQueries(): MutableSet<Query> {
        return LinkedHashSet()
    }

}
