package com.jester.marvel.ui.model

import com.jester.marvel.data.repository.character.model.ImageViewEntity


/**
 * Created by Héctor on 17/10/2017.
 */
data class ComicViewEntity(val id: Int,
                           val digitalId: Int,
                           val title: String?,
                           val description: String?,
                           val urls: List<UrlViewEntity>,
                           val series: SerieViewEntity,
                           val format: String,
                           val image: ImageViewEntity?)

data class UrlViewEntity(val type:String, val url: String)
data class SerieViewEntity(val name: String)