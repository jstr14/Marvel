package com.jester.marvel.model.story

import com.jester.marvel.model.image.Image


/**
 * Created by Héctor on 16/10/2017.
 */
data class Story(val id: Int,
                 val name: String?,
                 val description: String?,
                 val image: Image?) {
}