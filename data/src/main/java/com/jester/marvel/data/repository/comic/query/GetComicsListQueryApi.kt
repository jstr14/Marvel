package com.jester.marvel.data.repository.comic.query

import com.jester.marvel.Result
import com.jester.marvel.data.dependencyinjection.qualifier.retrofit.AuthenticationRetrofit
import com.jester.marvel.data.network.ConnectionChecker
import com.jester.marvel.data.network.parseResponse
import com.jester.marvel.data.network.service.ComicService
import com.jester.marvel.data.repository.comic.model.ComicDataEntity
import com.jester.marvel.model.exceptions.NetworkException
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Héctor on 22/11/2017.
 */
class GetComicsListQueryApi @Inject constructor(@AuthenticationRetrofit val retrofit: Retrofit,
                                                val connectionChecker: ConnectionChecker): GetComicsListQuery {

    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<*>, *> {

        if (connectionChecker.thereIsConnectivity()) {

            val offset = parameters?.get(GetComicsListQuery.Parameters.OFFSET) as Int
            val queryName = parameters[GetComicsListQuery.Parameters.QUERYNAME] as String
            val comicService = retrofit.create(ComicService::class.java)

            val response = comicService.getComicsList(offset).execute()

            if(response.isSuccessful){

                val resultList = response.parseResponse<List<ComicDataEntity>>(arrayListOf("data","results"))

                return resultList
            }

            return Result.Failure()

        }
        return Result.Failure(NetworkException.NoInternetConnection())
    }

}