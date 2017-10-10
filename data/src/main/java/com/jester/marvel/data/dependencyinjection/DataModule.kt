package com.jester.marvel.data.dependencyinjection

import com.jester.marvel.data.dependencyinjection.qualifier.CharactersApiQuery
import com.jester.marvel.data.dependencyinjection.qualifier.DefaultQueries
import com.jester.marvel.data.network.ApiConstants
import com.jester.marvel.data.network.AuthenticationInterceptor
import com.jester.marvel.data.repository.character.CharacterApiDataSource
import com.jester.marvel.data.repository.character.CharacterDataRepository
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.character.query.GetCharacterListQueryApi
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.query.Query
import com.jester.marvel.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val authenticationInterceptor = AuthenticationInterceptor()
        httpClient.addInterceptor(authenticationInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ApiConstants.BASEURL)
                    .build()


    @Provides
    @Singleton
    fun providesCharacterRepository(characterDataRepository: CharacterDataRepository): CharacterRepository {
        return characterDataRepository
    }

    @Provides
    @Singleton
    fun providesApiCharacterReadableDataSource(characterApiDataSource: CharacterApiDataSource): ReadableDataSource<String, CharacterDataEntity> {
        return characterApiDataSource
    }

    @Provides
    @Singleton
    @ElementsIntoSet
    @CharactersApiQuery
    fun providesGetCharactersListQuery(getCharacterListQueryApi: GetCharacterListQueryApi): MutableSet<Query> {

        val set = LinkedHashSet<Query>()
        set.add(getCharacterListQueryApi)
        return set
    }
}
