package com.jester.marvel.data.repository.comic.model

import com.jester.marvel.data.repository.character.model.mapToImage
import com.jester.marvel.model.comic.Comic
import com.jester.marvel.model.comic.Serie
import com.jester.marvel.model.comic.Url

/**
 * Created by Héctor on 16/10/2017.
 */

fun ComicDataEntity.mapToComic(): Comic {

    return Comic(this.id,
            this.digitalId,
            this.title,
            this.description,
            this.urls.map { it.mapToUrl() },
            this.series.mapToSerie(),
            this.format,
            this.image?.mapToImage())
}

fun UrlDataEntity.mapToUrl(): Url {

    return Url(this.type,this.url)
}

fun SerieDataEntity.mapToSerie(): Serie {

    return Serie(this.name)
}