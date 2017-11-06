package com.jester.marvel.data.repository.character.model.realm

import io.realm.RealmObject

/**
 * Created by Héctor on 17/10/2017.
 */
open class ImageRealmDataEntity(): RealmObject() {

    var path: String? = ""
    var extension: String? = ""

    constructor(path: String, extension: String) : this(){
        this.path = path
        this.extension = extension
    }


}