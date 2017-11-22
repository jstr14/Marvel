package com.jester.marvel.data.repository.comic.query

import com.jester.marvel.data.repository.query.Query

/**
 * Created by Héctor on 16/10/2017.
 */
interface GetComicsListByCharacterIdQuery : Query {

    companion object Parameters {
        const val ID = "ID"
    }
}