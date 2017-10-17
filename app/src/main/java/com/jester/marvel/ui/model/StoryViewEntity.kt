package com.jester.marvel.ui.model

import com.jester.marvel.data.repository.character.model.ImageViewEntity

/**
 * Created by Héctor on 17/10/2017.
 */
data class StoryViewEntity(val id: Int,
                           val title: String?,
                           val description: String?,
                           val image: ImageViewEntity?)