package com.jester.marvel.data.repository.event.query

import com.jester.marvel.data.repository.query.Query

/**
 * Created by Héctor on 16/10/2017.
 */
interface GetEventsListQuery : Query {

    companion object Parameters {
        const val ID = "ID"
    }
}