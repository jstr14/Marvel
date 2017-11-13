package com.jester.marvel.repository

import com.jester.marvel.Result
import com.jester.marvel.model.character.Character

/**
 * Created by Héctor on 10/10/2017.
 */
interface CharacterRepository {

    fun getCharacters(offset: Int): Result<List<Character>, Exception>
    fun getCharacterById(id: String): Result<Character, Exception>
    fun saveCharacter(character: Character): Result<Character, Exception>
    fun removeCharacter(id: String): Result<Unit,Exception>
    fun getFavCharacters(): Result<List<Character>, Exception>
    fun checkIfCharacterIsFav(id:String): Result<Character,Exception>
}