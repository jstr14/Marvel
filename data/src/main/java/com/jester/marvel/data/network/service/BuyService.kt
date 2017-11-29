package com.jester.marvel.data.network.service

import com.google.gson.JsonElement
import com.jester.marvel.data.network.ApiConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Héctor on 16/10/2017.
 */
interface BuyService {


    @GET(ApiConstants.EBAY_SEARCH)
    fun searchProductEbay(@Query("OPERATION-NAME")operation:String,
                          @Query("SERVICE-NAME")service:String,
                          @Query("SERVICE-VERSION") version: String,
                          @Query("SECURITY-APPNAME") appID: String,
                          @Query("RESPONSE-DATA-FORMAT") responseFormat: String,
                          @Query("REST-PAYLOAD&productId.@type")idSearchType: String,
                          @Query("productId")productId: String): Call<JsonElement>

}