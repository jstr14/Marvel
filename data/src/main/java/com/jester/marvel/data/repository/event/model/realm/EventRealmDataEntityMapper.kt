package com.jester.marvel.data.repository.event.model.realm

import com.jester.marvel.data.repository.character.model.realm.mapRealmDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToImageDataEntity
import com.jester.marvel.data.repository.event.model.EventDataEntity

/**
 * Created by Héctor on 17/10/2017.
 */
fun EventRealmDataEntity.mapToEventDataEntity(): EventDataEntity {

    return EventDataEntity(this.id,this.title,this.description,this.image?.mapToImageDataEntity())
}

fun EventDataEntity.mapToRealmDataEntity(): EventRealmDataEntity {

    return EventRealmDataEntity(this.id,this.title!!,this.description!!,this.image!!.mapRealmDataEntity())
}