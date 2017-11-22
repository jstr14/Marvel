package com.jester.marvel.data.network.service

import com.google.gson.JsonElement
import com.jester.marvel.data.network.ApiConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Héctor on 16/10/2017.
 */
interface ComicService {

    @GET(ApiConstants.GET_CHARACTER_COMICS)
    fun getComicsFromCharacterInfo(@Path("id")id: String): Call<JsonElement>

    @GET(ApiConstants.GET_COMICS)
    fun getComicsList(@Query("offset")offset:Int): Call<JsonElement>

    @GET(ApiConstants.GET_COMICS)
    fun getComicsListQueryName(@Query("offset")offset:Int,
                      @Query("nameStartsWith")queryName:String): Call<JsonElement>
}