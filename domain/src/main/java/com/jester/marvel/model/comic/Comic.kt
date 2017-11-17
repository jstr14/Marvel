package com.jester.marvel.model.comic

import com.jester.marvel.model.image.Image

/**
 * Created by Héctor on 16/10/2017.
 */
class Comic (val id: Int,
             val digitalId: Int,
             val title: String?,
             val description: String?,
             val image: Image?) {
}