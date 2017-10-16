package com.jester.marvel.data.repository.character.query

import com.jester.marvel.data.repository.query.Query

/**
 * Created by Héctor on 10/10/2017.
 */
interface GetCharacterListQuery: Query {

    companion object Parameters {
        const val OFFSET = "OFFSET"
        const val NAME = "name"
    }
}