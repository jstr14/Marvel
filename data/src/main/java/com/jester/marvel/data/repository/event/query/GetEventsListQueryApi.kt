package com.jester.marvel.data.repository.event.query

import com.jester.marvel.Result
import com.jester.marvel.data.network.ConnectionChecker
import com.jester.marvel.data.network.parseResponse
import com.jester.marvel.data.network.service.EventService
import com.jester.marvel.data.repository.event.model.EventDataEntity
import com.jester.marvel.model.exceptions.NetworkException
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class GetEventsListQueryApi @Inject constructor(val retrofit: Retrofit,
                                                val connectionChecker: ConnectionChecker): GetEventsListQuery {

    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<*>, *> {

        if (connectionChecker.thereIsConnectivity()) {

            val id = parameters?.get(GetEventsListQuery.Parameters.ID) as String
            val eventService = retrofit.create(EventService::class.java)
            val response = eventService.getEventsFromCharacterInfo(id).execute()

            if(response.isSuccessful){

                val resultList = response.parseResponse<List<EventDataEntity>>(arrayListOf("data","results"))

                return resultList
            }

            return Result.Failure()

        }
        return Result.Failure(NetworkException.NoInternetConnection())
    }

}