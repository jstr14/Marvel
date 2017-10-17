package com.jester.marvel.model.character

import com.jester.marvel.model.comic.Comic
import com.jester.marvel.model.event.Event
import com.jester.marvel.model.image.Image
import com.jester.marvel.model.story.Story

/**
 * Created by Héctor on 10/10/2017.
 */
data class Character(val id: String,
                     val name: String,
                     val image: Image,
                     var comics: List<Comic> = arrayListOf(),
                     var events: List<Event> = arrayListOf(),
                     var stories: List<Story> = arrayListOf())