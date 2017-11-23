package com.jester.marvel.data.repository.comic.model.realm

import com.jester.marvel.data.repository.character.model.realm.ImageRealmDataEntity
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by Héctor on 17/10/2017.
 */
open class ComicRealmDataEntity(): RealmObject() {

    var id: Int = 0
    var digitalId: Int = 0
    var title: String? = ""
    var description: String? = ""
    lateinit var urls: RealmList<UrlRealmDataEntity>
    lateinit var serie: SerieRealmDataEntity
    var image: ImageRealmDataEntity? = null

    constructor(id: Int, digitalId: Int, title: String, description: String, image: ImageRealmDataEntity): this(){

        this.id = id
        this.digitalId = digitalId
        this.title = title
        this.description = description
        this.image = image
    }
}

open class UrlRealmDataEntity(): RealmObject(){

    var type:String = ""
    var url: String = ""

    constructor(type:String,url:String):this(){

        this.type = type
        this.url = url
    }
}
open class SerieRealmDataEntity(): RealmObject(){
    var name: String = ""

    constructor(name:String):this(){
        this.name = name
    }
}