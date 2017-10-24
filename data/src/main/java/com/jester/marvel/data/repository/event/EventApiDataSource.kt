package com.jester.marvel.data.repository.event

import com.jester.marvel.data.dependencyinjection.qualifier.queries.EventApiQuery
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.event.model.EventDataEntity
import com.jester.marvel.data.repository.query.Query
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class EventApiDataSource @Inject constructor(@EventApiQuery override val queries: MutableSet<Query>)
    : ReadableDataSource<String, EventDataEntity> {
}