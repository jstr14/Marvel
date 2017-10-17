package com.jester.marvel.data.repository.event.model

import com.jester.marvel.data.repository.character.model.mapToImage
import com.jester.marvel.model.event.Event

/**
 * Created by Héctor on 16/10/2017.
 */
fun EventDataEntity.mapToEvent(): Event {

    return Event(this.id,
            this.title,
            this.description,
            this.image?.mapToImage())
}