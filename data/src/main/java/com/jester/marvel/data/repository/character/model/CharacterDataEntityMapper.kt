package com.jester.marvel.data.repository.character.model

import com.jester.marvel.model.character.Character
import com.jester.marvel.model.image.Image

/**
 * Created by Héctor on 10/10/2017.
 */
fun CharacterDataEntity.mapToCharacter(): Character {

    return Character(this.id,
            this.name,
            this.image.mapToImage())
}

fun ImageDataEntity.mapToImage(): Image {

    return Image(this.path, this.extension)
}