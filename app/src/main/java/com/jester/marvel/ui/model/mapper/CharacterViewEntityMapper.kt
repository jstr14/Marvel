package com.jester.marvel.ui.model.mapper

import com.jester.marvel.model.character.Character
import com.jester.marvel.ui.model.CharacterViewEntity

/**
 * Created by Héctor on 11/10/2017.
 */
fun Character.mapToCharacterViewEntity() : CharacterViewEntity {

    return CharacterViewEntity(this.name)
}

fun CharacterViewEntity.mapToCharacter(): Character{

    return Character(this.name)
}