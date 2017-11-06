package com.jester.marvel.data.repository.comic.model

import com.jester.marvel.data.repository.character.model.mapToImage
import com.jester.marvel.model.comic.Comic

/**
 * Created by Héctor on 16/10/2017.
 */

fun ComicDataEntity.mapToComic(): Comic {

    return Comic(this.id,
            this.digitalId,
            this.title,
            this.description,
            this.image?.mapToImage())
}