package com.jester.marvel.data.repository.story.model

import com.google.gson.annotations.SerializedName
import com.jester.marvel.data.repository.character.model.ImageDataEntity

/**
 * Created by Héctor on 16/10/2017.
 */
data class StoryDataEntity (val id: Int,
                            val title: String,
                            val description: String?,
                            @SerializedName("thumbnail") val image: ImageDataEntity?)