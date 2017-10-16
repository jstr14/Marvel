package com.jester.marvel.model.character

import com.jester.marvel.model.image.Image

/**
 * Created by Héctor on 10/10/2017.
 */
data class Character(val id: String,
                     val name: String,
                     val image: Image)