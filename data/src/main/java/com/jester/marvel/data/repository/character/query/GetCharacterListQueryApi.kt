package com.jester.marvel.data.repository.character.query

import com.jester.marvel.Result
import com.jester.marvel.data.network.ConnectionChecker
import com.jester.marvel.data.network.parseResponse
import com.jester.marvel.data.network.service.CharacterService
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.model.exceptions.NetworkException
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by Héctor on 10/10/2017.
 */
class GetCharacterListQueryApi @Inject constructor(val retrofit: Retrofit,
                                                   val connectionChecker: ConnectionChecker): GetCharacterListQuery{

    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<*>, *> {

        if (connectionChecker.thereIsConnectivity()) {

            val offset = parameters?.get(GetCharacterListQuery.Parameters.OFFSET) as Int

            val userProfileService = retrofit.create(CharacterService::class.java)
            val charactersCall =userProfileService.getCharactersList(offset)

            val response = charactersCall.execute()

            if(response.isSuccessful){

                val resultList = response.parseResponse<List<CharacterDataEntity>>(arrayListOf("data","results"))

                return resultList
            }

            return Result.Failure()

        }
        return Result.Failure(NetworkException.NoInternetConnection())
    }

}