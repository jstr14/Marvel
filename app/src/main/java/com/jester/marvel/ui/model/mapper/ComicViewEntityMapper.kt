package com.jester.marvel.ui.model.mapper

import com.jester.marvel.model.comic.Comic
import com.jester.marvel.ui.model.ComicViewEntity

/**
 * Created by Héctor on 17/10/2017.
 */
fun Comic.mapToComicViewEntity() : ComicViewEntity {

    return ComicViewEntity(this.id,this.digitalId,this.title,this.description,this.image?.mapToImageViewEntitiy())
}

fun ComicViewEntity.mapToComic(): Comic{

    return Comic(this.id,this.digitalId,this.title,this.description,this.image?.mapToImage())
}