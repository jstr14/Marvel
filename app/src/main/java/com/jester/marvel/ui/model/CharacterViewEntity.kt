package com.jester.marvel.ui.model

import com.jester.marvel.data.repository.character.model.ImageViewEntity

/**
 * Created by Héctor on 11/10/2017.
 */
data class CharacterViewEntity(val id: String,
                               val name: String,
                               val image: ImageViewEntity)