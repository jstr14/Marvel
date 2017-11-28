package com.jester.marvel.data.repository.comic.model

import com.google.gson.annotations.SerializedName
import com.jester.marvel.data.repository.character.model.ImageDataEntity

/**
 * Created by Héctor on 16/10/2017.
 */
data class ComicDataEntity (val id: Int,
                            val digitalId: Int,
                            val title: String?,
                            val description: String?,
                            val urls: List<UrlDataEntity>,
                            val series: SerieDataEntity,
                            val format: String,
                            @SerializedName("thumbnail") val image: ImageDataEntity?)

data class UrlDataEntity(val type:String, val url: String)
data class SerieDataEntity(val name: String)