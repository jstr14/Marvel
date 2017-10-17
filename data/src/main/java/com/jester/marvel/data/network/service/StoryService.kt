package com.jester.marvel.data.network.service

import com.google.gson.JsonElement
import com.jester.marvel.data.network.ApiConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Héctor on 16/10/2017.
 */
interface StoryService {

    @GET(ApiConstants.GET_CHARACTER_STORIES)
    fun getStoriesFromCharacterInfo(@Path("id")id: String): Call<JsonElement>
}