package com.jester.marvel.data.repository.story.query

import com.jester.marvel.Result
import com.jester.marvel.data.network.ConnectionChecker
import com.jester.marvel.data.network.parseResponse
import com.jester.marvel.data.network.service.StoryService
import com.jester.marvel.data.repository.story.model.StoryDataEntity
import com.jester.marvel.model.exceptions.NetworkException
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class GetStoriesListQueryApi @Inject constructor(val retrofit: Retrofit,
                                                 val connectionChecker: ConnectionChecker): GetStoriesListQuery {

    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<*>, *> {

        if (connectionChecker.thereIsConnectivity()) {

            val id = parameters?.get(GetStoriesListQuery.Parameters.ID) as String
            val storyService = retrofit.create(StoryService::class.java)
            val response = storyService.getStoriesFromCharacterInfo(id).execute()

            if(response.isSuccessful){

                val resultList = response.parseResponse<List<StoryDataEntity>>(arrayListOf("data","results"))

                return resultList
            }

            return Result.Failure()

        }
        return Result.Failure(NetworkException.NoInternetConnection())
    }

}