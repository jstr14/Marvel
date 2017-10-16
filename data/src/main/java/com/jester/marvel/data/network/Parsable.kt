package com.jester.marvel.data.network

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.jester.marvel.Result
import com.jester.marvel.model.exceptions.NetworkException
import retrofit2.Response

/**
 * Created by Héctor on 10/10/2017.
 */
val TAG_DATA = "data"

inline fun<reified T> Response<JsonElement>.parseJsonResponse(rootTag: String,
                                                              jsonObject : String = ""): T {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    var jsonResponse : JsonElement
    if(rootTag.isEmpty()){
        jsonResponse = this.body()!!.asJsonObject

    } else {
        jsonResponse = this.body()!!.asJsonObject.get(rootTag)
    }
    if (jsonObject.isNotEmpty()) {
        val parsedResponse: T = gson.fromJson(jsonResponse.asJsonObject.get(jsonObject), type)
        return parsedResponse
    } else {
        val parsedResponse: T = gson.fromJson(jsonResponse, type)
        return parsedResponse
    }
}
inline fun<reified T> Response<JsonElement>.parseResponse(rootTag: String = TAG_DATA,
                                                          jsonObject: String = ""): Result<T, Exception> {
    return if (this.isSuccessful) {
        Result.of {
            this.parseJsonResponse<T>(rootTag, jsonObject)
        }
    } else {
        Result.Failure(getExceptionFromHttpErrorCode(this.code()))
    }
}
fun getExceptionFromHttpErrorCode(code: Int): Exception {
    when (code) {
        401 -> return NetworkException.UnauthorizedException()
        500 -> return NetworkException.ServerException()
        else -> return Exception()
    }
}


inline fun<reified T> Response<JsonElement>.parseResponse(tags: List<String>, jsonObject: String = ""): Result<T,Exception> {

    return if(this.isSuccessful){
        Result.of {
            this.parseJsonResponse<T>(tags,jsonObject)
        }
    } else {
        Result.Failure(getExceptionFromHttpErrorCode(this.code()))
    }
}

inline fun<reified T> Response<JsonElement>.parseJsonResponse(tags: List<String>,
                                                              jsonObject : String = ""): T {
    val gson = Gson()
    val type = object : TypeToken<T>() {}.type
    var jsonResponse : JsonElement
    if(tags.size >= 0){

        jsonResponse = this.body()!!
        for (tag in tags) {

            jsonResponse = jsonResponse.getJsonObjectFromTag(tag)!!
        }
    } else {
        jsonResponse = this.body()!!.asJsonObject
    }
    return if (jsonObject.isNotEmpty()) {
        val parsedResponse: T = gson.fromJson(jsonResponse.asJsonObject.get(jsonObject), type)
        parsedResponse
    } else {
        val parsedResponse: T = gson.fromJson(jsonResponse, type)
        parsedResponse
    }
}

inline fun JsonElement.getJsonObjectFromTag(tag:String): JsonElement? {

   return this.asJsonObject.get(tag)

}