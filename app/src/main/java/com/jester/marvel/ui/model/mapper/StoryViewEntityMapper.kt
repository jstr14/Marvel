package com.jester.marvel.ui.model.mapper

import com.jester.marvel.model.story.Story
import com.jester.marvel.ui.model.StoryViewEntity

/**
 * Created by Héctor on 17/10/2017.
 */
fun Story.mapToEventViewEntity() : StoryViewEntity {

    return StoryViewEntity(this.id,this.name,this.description,this.image?.mapToImageViewEntitiy())
}

fun StoryViewEntity.mapToStory(): Story {

    return Story(this.id,this.title,this.description,this.image?.mapToImage())
}