package com.jester.marvel.data.repository.event

import com.jester.marvel.Result
import com.jester.marvel.data.repository.Repository
import com.jester.marvel.data.repository.event.model.EventDataEntity
import com.jester.marvel.data.repository.event.model.mapToEvent
import com.jester.marvel.data.repository.event.query.GetEventsListQuery
import com.jester.marvel.model.event.Event
import com.jester.marvel.repository.EventRepository
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class EventDataRepository @Inject constructor(eventApiDataSource: EventApiDataSource)
    : EventRepository, Repository<String, EventDataEntity>() {
    init {
        readableDataSources.add(eventApiDataSource)
    }

    override fun getCharacterEvents(id: String): Result<List<Event>, Exception> {

        val params = HashMap<String, Any>()
        params.put(GetEventsListQuery.ID, id)
        val result = queryAll(GetEventsListQuery::class.java, params)
        return result.map { it.map { it.mapToEvent() } }
    }
}
