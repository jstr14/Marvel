package com.jester.marvel.ui.model.mapper

import com.jester.marvel.model.comic.Comic
import com.jester.marvel.model.comic.Serie
import com.jester.marvel.model.comic.Url
import com.jester.marvel.ui.model.ComicViewEntity
import com.jester.marvel.ui.model.SerieViewEntity
import com.jester.marvel.ui.model.UrlViewEntity

/**
 * Created by Héctor on 17/10/2017.
 */
fun Comic.mapToComicViewEntity(): ComicViewEntity {

    return ComicViewEntity(this.id
            , this.digitalId,
            this.title,
            this.description,
            this.urls.map { it.mapToUrlViewEntity() },
            this.series.mapToSerieViewEntity(),
            this.format,
            this.image?.mapToImageViewEntitiy())
}

fun ComicViewEntity.mapToComic(): Comic {

    return Comic(this.id,
            this.digitalId,
            this.title,
            this.description,
            this.urls.map { it.mapToUrl() },
            this.series.mapToSerie(),
            this.format,
            this.image?.mapToImage())
}

fun Url.mapToUrlViewEntity(): UrlViewEntity {

    return UrlViewEntity(this.type, this.url)
}

fun Serie.mapToSerieViewEntity(): SerieViewEntity {

    return SerieViewEntity(this.name)
}

fun UrlViewEntity.mapToUrl(): Url {
    return Url(this.type,this.url)
}

fun SerieViewEntity.mapToSerie(): Serie {
    return Serie(this.name)
}