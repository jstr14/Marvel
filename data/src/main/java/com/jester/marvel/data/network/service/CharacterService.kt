package com.jester.marvel.data.network.service

import com.google.gson.JsonElement
import com.jester.marvel.data.network.ApiConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Héctor on 10/10/2017.
 */
interface CharacterService {

    @GET(ApiConstants.GET_CHARACTERS_LIST)
    fun getCharactersList(@Query("orderBy") orderBy: String,
                          @Query("offset") offset: Int): Call<JsonElement>
}