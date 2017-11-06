package com.jester.marvel.data.repository.character.model.realm

import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.character.model.ImageDataEntity
import com.jester.marvel.data.repository.comic.model.realm.ComicRealmDataEntity
import com.jester.marvel.data.repository.comic.model.realm.mapToComicDataEntity
import com.jester.marvel.data.repository.comic.model.realm.mapToRealmDataEntity
import com.jester.marvel.data.repository.event.model.realm.EventRealmDataEntity
import com.jester.marvel.data.repository.event.model.realm.mapToEventDataEntity
import com.jester.marvel.data.repository.event.model.realm.mapToRealmDataEntity
import com.jester.marvel.data.repository.story.model.realm.StoryRealmDataEntity
import com.jester.marvel.data.repository.story.model.realm.mapToRealmDataEntity
import com.jester.marvel.data.repository.story.model.realm.mapToStroyDataEntity
import io.realm.RealmList

/**
 * Created by Héctor on 17/10/2017.
 */
fun CharacterRealmDataEntity.mapToCharacterDataEntity(): CharacterDataEntity {

    return CharacterDataEntity(this.id,
            this.name,
            this.description,
            this.image?.mapToImageDataEntity(),
            this.comics?.map { it.mapToComicDataEntity() },
            this.events?.map { it.mapToEventDataEntity() },
            this.stories?.map { it.mapToStroyDataEntity() })
}


fun ImageRealmDataEntity.mapToImageDataEntity(): ImageDataEntity {

    return ImageDataEntity(this.path,this.extension)
}

fun ImageDataEntity.mapRealmDataEntity(): ImageRealmDataEntity {
    return ImageRealmDataEntity(this.path!!,this.extension!!)
}

fun CharacterDataEntity.mapToRealmDataEntity(): CharacterRealmDataEntity {

    val comicsRealm = RealmList<ComicRealmDataEntity>()
    val comicsMap = this.comicsList?.map { it.mapToRealmDataEntity() }

    comicsMap?.let { comicsRealm.addAll(comicsMap) }

    val eventsRealm = RealmList<EventRealmDataEntity>()
    val eventsMap= this.eventsList?.map { it.mapToRealmDataEntity() }
    eventsMap?.let { eventsRealm.addAll(eventsMap) }

    val storiesRealm = RealmList<StoryRealmDataEntity>()
    val storiesMap = this.storiesList?.map { it.mapToRealmDataEntity() }
    storiesMap?.let { storiesRealm.addAll(storiesMap) }

    return CharacterRealmDataEntity(this.id,
            this.name,
            this.description,
            this.image!!.mapRealmDataEntity(),
            comicsRealm,
            eventsRealm,
            storiesRealm)
}