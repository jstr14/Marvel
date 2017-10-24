package com.jester.marvel.data.repository.comic.model.realm

import com.jester.marvel.data.repository.character.model.realm.ImageRealmDataEntity
import io.realm.RealmObject

/**
 * Created by Héctor on 17/10/2017.
 */
open class ComicRealmDataEntity(): RealmObject() {

    var id: Int = 0
    var digitalId: Int = 0
    var title: String? = ""
    var description: String? = ""
    var image: ImageRealmDataEntity? = null

    constructor(id: Int, digitalId: Int, title: String, description: String, image: ImageRealmDataEntity): this(){

        this.id = id
        this.digitalId = digitalId
        this.title = title
        this.description = description
        this.image = image
    }
}
