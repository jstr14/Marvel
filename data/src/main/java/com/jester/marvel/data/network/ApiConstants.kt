package com.jester.marvel.data.network

/**
 * Created by Héctor on 10/10/2017.
 */
interface ApiConstants {

    companion object URL {
        const val BASEURL = "https://gateway.marvel.com:443/v1/public/"
        const val GET_CHARACTERS_LIST = "characters"
    }
}