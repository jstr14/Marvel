package com.jester.marvel.data.network

/**
 * Created by Héctor on 10/10/2017.
 */
interface ApiConstants {

    companion object URL {
        const val BASEURL = "https://gateway.marvel.com:443/v1/public/"
        const val GET_CHARACTERS_LIST = "characters"
        const val GET_CHARACTER_INFO = "characters/{id}"
        const val GET_CHARACTER_EVENTS = "characters/{id}/events"
        const val GET_CHARACTER_STORIES = "characters/{id}/stories"
        const val GET_CHARACTER_COMICS = "characters/{id}/comics"
        const val GET_COMICS = "comics"
        const val EBAY_BASE_URL = "https://svcs.ebay.com/services/"
        const val EBAY_SEARCH = "search/FindingService/v1"
    }
}