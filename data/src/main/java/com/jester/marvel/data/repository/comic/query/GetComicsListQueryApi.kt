package com.jester.marvel.data.repository.comic.query

import com.jester.marvel.Result
import com.jester.marvel.data.network.ConnectionChecker
import com.jester.marvel.data.network.parseResponse
import com.jester.marvel.data.network.service.ComicService
import com.jester.marvel.data.repository.comic.model.ComicDataEntity
import com.jester.marvel.model.exceptions.NetworkException
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class GetComicsListQueryApi @Inject constructor(val retrofit: Retrofit,
                                                val connectionChecker: ConnectionChecker): GetComicsListQuery {

    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<*>, *> {

        if (connectionChecker.thereIsConnectivity()) {

            val id = parameters?.get(GetComicsListQuery.Parameters.ID) as String
            val comicService = retrofit.create(ComicService::class.java)
            val response = comicService.getComicsFromCharacterInfo(id).execute()

            if(response.isSuccessful){

                val resultList = response.parseResponse<List<ComicDataEntity>>(arrayListOf("data","results"))

                return resultList
            }

            return Result.Failure()

        }
        return Result.Failure(NetworkException.NoInternetConnection())
    }

}