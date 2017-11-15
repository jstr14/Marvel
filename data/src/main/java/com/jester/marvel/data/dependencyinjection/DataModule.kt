package com.jester.marvel.data.dependencyinjection

import com.jester.marvel.data.dependencyinjection.qualifier.DiskCacheTtl
import com.jester.marvel.data.dependencyinjection.qualifier.queries.*
import com.jester.marvel.data.network.ApiConstants
import com.jester.marvel.data.network.AuthenticationInterceptor
import com.jester.marvel.data.repository.character.CharacterApiDataSource
import com.jester.marvel.data.repository.character.CharacterCacheDataStore
import com.jester.marvel.data.repository.character.CharacterDataRepository
import com.jester.marvel.data.repository.character.CharacterRealmDataSource
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.character.query.CheckIfCharacterIsFavQueryDisk
import com.jester.marvel.data.repository.character.query.GetCharacterListQueryApi
import com.jester.marvel.data.repository.character.query.GetCharacterListQueryByNameApi
import com.jester.marvel.data.repository.character.query.GetFavCharactersQueryDisk
import com.jester.marvel.data.repository.comic.ComicApiDataSource
import com.jester.marvel.data.repository.comic.ComicDataRepository
import com.jester.marvel.data.repository.comic.model.ComicDataEntity
import com.jester.marvel.data.repository.comic.query.GetComicsListQueryApi
import com.jester.marvel.data.repository.datasource.*
import com.jester.marvel.data.repository.event.EventApiDataSource
import com.jester.marvel.data.repository.event.EventDataRepository
import com.jester.marvel.data.repository.event.model.EventDataEntity
import com.jester.marvel.data.repository.event.query.GetEventsListQueryApi
import com.jester.marvel.data.repository.query.Query
import com.jester.marvel.data.repository.story.StoryApiDataSource
import com.jester.marvel.data.repository.story.StoryDataRepository
import com.jester.marvel.data.repository.story.model.StoryDataEntity
import com.jester.marvel.data.repository.story.query.GetStoriesListQueryApi
import com.jester.marvel.repository.CharacterRepository
import com.jester.marvel.repository.ComicRepository
import com.jester.marvel.repository.EventRepository
import com.jester.marvel.repository.StoryRepository
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
    @Singleton
    fun providesTTLCache(): Long {
        return 60000
    }

    @Provides
    @Singleton
    @DiskCacheTtl
    fun providesTTLDisk(): Long {
        return 60000 * 5
    }

    @Provides
    @Singleton
    fun ProvidesTimeProvider(): TimeProvider {
        return SystemTimeProvider()
    }

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
    fun providesCacheCharacterRedableDataSource(characterCacheDataStore: CharacterCacheDataStore): InMemoryCacheDataSource<String,CharacterDataEntity> {
        return characterCacheDataStore
    }

    @Provides
    @Singleton
    fun providesRealmReadableCharacterDataSource(characterRealmDataSource: CharacterRealmDataSource): ReadableDataSource<String,CharacterDataEntity>{
        return characterRealmDataSource
    }

    @Provides
    @Singleton
    fun providesRealmWritableCharacterDataSource(characterRealmDataSource: CharacterRealmDataSource): WritableDataSource<String,CharacterDataEntity>{
        return characterRealmDataSource
    }

    @Provides
    @Singleton
    @ElementsIntoSet
    @CharactersDiskQuery
    fun providesGetCharactersListDiskQuery(getFavCharactersQueryDisk: GetFavCharactersQueryDisk,
                                           checkIfCharacterIsFavQueryDisk: CheckIfCharacterIsFavQueryDisk): MutableSet<Query> {

        val set = LinkedHashSet<Query>()
        set.add(getFavCharactersQueryDisk)
        set.add(checkIfCharacterIsFavQueryDisk)
        return set
    }

    @Provides
    @Singleton
    @ElementsIntoSet
    @CharactersApiQuery
    fun providesGetCharactersListQuery(getCharacterListQueryApi: GetCharacterListQueryApi, getCharactersListQueryByNameApi: GetCharacterListQueryByNameApi): MutableSet<Query> {

        val set = LinkedHashSet<Query>()
        set.add(getCharacterListQueryApi)
        set.add(getCharactersListQueryByNameApi)
        return set
    }


    @Provides
    @Singleton
    fun providesComicRepository(comicDataRepository: ComicDataRepository): ComicRepository {
        return comicDataRepository
    }

    @Provides
    @Singleton
    fun providesApiComicReadableDataSource(comicApiDataSource: ComicApiDataSource): ReadableDataSource<String,ComicDataEntity> {
        return comicApiDataSource
    }

    @Provides
    @Singleton
    @ElementsIntoSet
    @ComicsApiQuery
    fun providesGetComicsListQuery(getComicsListQueryApi: GetComicsListQueryApi) : MutableSet<Query> {

        val set = LinkedHashSet<Query>()
        set.add(getComicsListQueryApi)
        return set
    }

    @Provides
    @Singleton
    fun providesEventRepository(eventDataRepository: EventDataRepository): EventRepository {
        return eventDataRepository
    }

    @Provides
    @Singleton
    fun providesApiEventReadableDataSource(eventApiDataSource: EventApiDataSource): ReadableDataSource<String,EventDataEntity> {
        return eventApiDataSource
    }

    @Provides
    @Singleton
    @ElementsIntoSet
    @EventApiQuery
    fun providesGetEventsListQuery(getEventsListQueryApi: GetEventsListQueryApi) : MutableSet<Query> {

        val set = LinkedHashSet<Query>()
        set.add(getEventsListQueryApi)
        return set
    }

    @Provides
    @Singleton
    fun providesStoriesRepository(storyDataRepository: StoryDataRepository) : StoryRepository {
        return  storyDataRepository
    }

    @Provides
    @Singleton
    fun providesApiStoryReadableDataSource(storyApiDataSource: StoryApiDataSource): ReadableDataSource<String, StoryDataEntity> {
        return storyApiDataSource
    }

    @Provides
    @Singleton
    @ElementsIntoSet
    @StoryApiQuery
    fun providesGetStoriesListQuery(getStoriesListQueryApi: GetStoriesListQueryApi): MutableSet<Query>{
        val set = LinkedHashSet<Query>()
        set.add(getStoriesListQueryApi)
        return set
    }
}
