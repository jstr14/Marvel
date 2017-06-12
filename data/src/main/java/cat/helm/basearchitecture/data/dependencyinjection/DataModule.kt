package cat.helm.basearchitecture.data.dependencyinjection

import cat.helm.basearchitecture.data.repository.query.Query
import cat.helm.ureentool.data.dependencyinjection.qualifier.queries.DefaultQueries
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
