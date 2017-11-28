package com.jester.marvel.data.repository.comic.model.realm

import com.jester.marvel.data.repository.character.model.realm.mapRealmDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToImageDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToSerieDataEntity
import com.jester.marvel.data.repository.comic.model.ComicDataEntity
import com.jester.marvel.data.repository.comic.model.SerieDataEntity
import com.jester.marvel.data.repository.comic.model.UrlDataEntity
import io.realm.RealmList

/**
 * Created by Héctor on 17/10/2017.
 */
fun ComicRealmDataEntity.mapToComicDataEntity(): ComicDataEntity {

    return ComicDataEntity(this.id, this.digitalId, this.title, this.description,this.urls.map { it.mapToUrlDataEntity() }, this.serie.mapToSerieDataEntity(),this.format, this.image?.mapToImageDataEntity())
}

fun ComicDataEntity.mapToRealmDataEntity(): ComicRealmDataEntity {

    val urlRealmDataEntityList = RealmList<UrlRealmDataEntity>()
    urlRealmDataEntityList.addAll(this.urls.map { it.mapToUrlRealmDataEntity() })

    return ComicRealmDataEntity(this.id, this.digitalId, this.title!!, this.description!!, urlRealmDataEntityList , this.series.mapToSerieRealmDataEntity(), this.format, this.image!!.mapRealmDataEntity())
}

fun UrlRealmDataEntity.mapToUrlDataEntity(): UrlDataEntity {

    return UrlDataEntity(this.type, this.url)
}

fun UrlDataEntity.mapToUrlRealmDataEntity(): UrlRealmDataEntity {

    return UrlRealmDataEntity(this.type, this.url)
}

fun SerieDataEntity.mapToSerieRealmDataEntity(): SerieRealmDataEntity {

    return SerieRealmDataEntity(this.name)
}

