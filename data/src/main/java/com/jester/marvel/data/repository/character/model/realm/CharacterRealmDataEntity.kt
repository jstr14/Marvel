package com.jester.marvel.data.repository.character.model.realm

import com.jester.marvel.data.repository.comic.model.realm.ComicRealmDataEntity
import com.jester.marvel.data.repository.event.model.realm.EventRealmDataEntity
import com.jester.marvel.data.repository.story.model.realm.StoryRealmDataEntity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Héctor on 17/10/2017.
 */
open class CharacterRealmDataEntity(): RealmObject() {

    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var description: String = ""
    var image: ImageRealmDataEntity? =  null
    var comics: RealmList<ComicRealmDataEntity>? = RealmList()
    var events: RealmList<EventRealmDataEntity>? = RealmList()
    var stories: RealmList<StoryRealmDataEntity>? = RealmList()

    constructor(id: String, name: String, description: String,
                image: ImageRealmDataEntity,
                comics: RealmList<ComicRealmDataEntity>,
                events: RealmList<EventRealmDataEntity>,
                stories: RealmList<StoryRealmDataEntity>) : this() {

        this.id = id
        this.name = name
        this.description = description
        this.image = image
        this.comics = comics
        this.events = events
        this.stories = stories

    }
}