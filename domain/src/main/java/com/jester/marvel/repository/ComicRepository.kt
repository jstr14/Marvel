package com.jester.marvel.repository

import com.jester.marvel.Result
import com.jester.marvel.model.comic.Comic

/**
 * Created by Héctor on 16/10/2017.
 */
interface ComicRepository {

    fun getCharacterComics(id:String): Result<List<Comic>, Exception>

}