package com.jester.marvel.ui.model.mapper

import com.jester.marvel.model.event.Event
import com.jester.marvel.ui.model.EventViewEntity

/**
 * Created by Héctor on 17/10/2017.
 */
fun Event.mapToEventViewEntity() : EventViewEntity {

    return EventViewEntity(this.id,this.name,this.description,this.image?.mapToImageViewEntitiy())
}

fun EventViewEntity.mapToEvent(): Event{

    return Event(this.id,this.title,this.description,this.image?.mapToImage())
}