package com.jester.marvel.ui.model.mapper

import com.jester.marvel.data.repository.character.model.ImageViewEntity
import com.jester.marvel.model.character.Character
import com.jester.marvel.model.image.Image
import com.jester.marvel.ui.model.CharacterViewEntity

/**
 * Created by Héctor on 11/10/2017.
 */
fun Character.mapToCharacterViewEntity() : CharacterViewEntity {

    return CharacterViewEntity(this.id,this.name,this.image.mapToImageViewEntitiy())
}

fun CharacterViewEntity.mapToCharacter(): Character{

    return Character(this.id,this.name,this.image.mapToImage())
}

fun Image.mapToImageViewEntitiy() : ImageViewEntity {

    return ImageViewEntity(this.path,this.extension)
}

fun ImageViewEntity.mapToImage(): Image{

    return Image(this.path,this.extension)
}