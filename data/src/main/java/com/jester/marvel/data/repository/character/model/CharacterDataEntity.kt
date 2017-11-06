package com.jester.marvel.data.repository.character.model

import com.google.gson.annotations.SerializedName
import com.jester.marvel.data.repository.comic.model.ComicDataEntity
import com.jester.marvel.data.repository.datasource.Identifiable
import com.jester.marvel.data.repository.event.model.EventDataEntity
import com.jester.marvel.data.repository.story.model.StoryDataEntity

/**
 * Created by Héctor on 10/10/2017.
 */
data class CharacterDataEntity(val id: String,
                               val name: String,
                               val description: String,
                               @SerializedName("thumbnail") val image: ImageDataEntity?,
                               val comicsList: List<ComicDataEntity>? = emptyList(),
                               val eventsList: List<EventDataEntity>? = emptyList(),
                               val storiesList: List<StoryDataEntity>? = emptyList()) : Identifiable<String> {

    override fun getKey(): String {
        return id
    }
}