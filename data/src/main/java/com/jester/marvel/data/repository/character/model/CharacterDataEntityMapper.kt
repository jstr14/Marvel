package com.jester.marvel.data.repository.character.model

import com.jester.marvel.model.character.Character

/**
 * Created by Héctor on 10/10/2017.
 */
fun CharacterDataEntity.mapToCharacter(): Character {

    return Character(this.name)
}