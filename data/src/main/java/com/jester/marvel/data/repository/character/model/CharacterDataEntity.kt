package com.jester.marvel.data.repository.character.model

import com.google.gson.annotations.SerializedName
import com.jester.marvel.data.repository.comic.model.ComicDataEntity

/**
 * Created by Héctor on 10/10/2017.
 */
data class CharacterDataEntity(val id: String,
                               val name: String,
                               @SerializedName("thumbnail") val image: ImageDataEntity,
                               val comicsList: List<ComicDataEntity> = arrayListOf())