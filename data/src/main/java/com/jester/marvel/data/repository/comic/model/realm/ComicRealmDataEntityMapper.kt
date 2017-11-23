package com.jester.marvel.data.repository.comic.model.realm

import com.jester.marvel.data.repository.character.model.realm.mapRealmDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToImageDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToSerieDataEntity
import com.jester.marvel.data.repository.comic.model.ComicDataEntity

/**
 * Created by Héctor on 17/10/2017.
 */
fun ComicRealmDataEntity.mapToComicDataEntity(): ComicDataEntity {

    return ComicDataEntity(this.id,this.digitalId,this.title,this.description, arrayListOf(),this.serie.mapToSerieDataEntity(),this.image?.mapToImageDataEntity())
}

fun ComicDataEntity.mapToRealmDataEntity(): ComicRealmDataEntity {

    return ComicRealmDataEntity(this.id,this.digitalId,this.title!!,this.description!!,this.image!!.mapRealmDataEntity())
}