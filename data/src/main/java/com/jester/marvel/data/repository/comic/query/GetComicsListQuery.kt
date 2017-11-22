package com.jester.marvel.data.repository.comic.query

import com.jester.marvel.data.repository.query.Query

/**
 * Created by Héctor on 22/11/2017.
 */
interface GetComicsListQuery: Query {

    companion object Parameters{

        const val OFFSET = "OFFSET"
        const val QUERYNAME = "QUERYNAME"
    }
}