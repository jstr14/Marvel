package com.jester.marvel.data.repository.story.model.realm

import com.jester.marvel.data.repository.character.model.realm.mapRealmDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToImageDataEntity
import com.jester.marvel.data.repository.story.model.StoryDataEntity

/**
 * Created by Héctor on 17/10/2017.
 */
fun StoryRealmDataEntity.mapToStroyDataEntity(): StoryDataEntity {

    return StoryDataEntity(this.id,this.title,this.description,this.image?.mapToImageDataEntity())
}

fun StoryDataEntity.mapToRealmDataEntity(): StoryRealmDataEntity {

    return StoryRealmDataEntity(this.id,this.title!!,this.description!!,this.image!!.mapRealmDataEntity())
}