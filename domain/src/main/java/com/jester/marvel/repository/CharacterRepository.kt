package com.jester.marvel.repository

import com.jester.marvel.Result
import com.jester.marvel.model.Character

/**
 * Created by Héctor on 10/10/2017.
 */
interface CharacterRepository {

    fun getCharacters(offset: Int): Result<List<Character>, Exception>
}