package com.jester.marvel.data.repository.character

import com.jester.marvel.Result
import com.jester.marvel.data.dependencyinjection.qualifier.queries.CharactersApiQuery
import com.jester.marvel.data.network.ConnectionChecker
import com.jester.marvel.data.network.parseResponse
import com.jester.marvel.data.network.service.CharacterService
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.query.Query
import com.jester.marvel.model.exceptions.NetworkException
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class CharacterApiDataSource @Inject constructor(@CharactersApiQuery override val queries: MutableSet<Query>,
                                                 val retrofit: Retrofit,
                                                 val connectionChecker: ConnectionChecker)
    : ReadableDataSource<String, CharacterDataEntity> {


    override fun getByKey(key: String): Result<CharacterDataEntity, *> {

        if (connectionChecker.thereIsConnectivity()) {
            val characterService = retrofit.create(CharacterService::class.java)
            val response = characterService.getCharacterInfo(key).execute()

            if(response.isSuccessful) {
                val result = response.parseResponse<List<CharacterDataEntity>>(arrayListOf("data", "results"))
                return result.map { it.first() }
            }

            return Result.Failure()
        }

        return Result.Failure(NetworkException.NoInternetConnection())
    }

}