package com.jester.marvel.data.repository.story.model

import com.jester.marvel.data.repository.character.model.mapToImage
import com.jester.marvel.model.story.Story

/**
 * Created by Héctor on 16/10/2017.
 */
fun StoryDataEntity.mapToStory(): Story {

    return Story(this.id,
            this.title,
            this.description,
            this.image?.mapToImage())
}