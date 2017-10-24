package com.jester.marvel.data.repository.event.model.realm

import com.jester.marvel.data.repository.character.model.realm.ImageRealmDataEntity
import io.realm.RealmObject

/**
 * Created by Héctor on 17/10/2017.
 */
open class EventRealmDataEntity(): RealmObject() {

    var id: Int = 0
    var title: String? = ""
    var description: String? = ""
    var image: ImageRealmDataEntity? = null

    constructor(id: Int, title: String, description: String, image: ImageRealmDataEntity): this(){

        this.id = id
        this.title = title
        this.description = description
        this.image = image
    }
}