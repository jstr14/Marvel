package com.jester.marvel.data.repository.character.query

import com.jester.marvel.data.repository.query.Query

/**
 * Created by Héctor on 10/11/2017.
 */
interface CheckIfCharacterIsFavQuery: Query {

    companion object Parameters{

        const val ID = "id"
    }
}