package com.jester.marvel.event

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.event.Event
import com.jester.marvel.repository.EventRepository
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class GetCharacterEventsInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                       val repository: EventRepository)
    : Interactor<List<Event>, GetCharacterEventsInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<List<Event>, *> {
        return repository.getCharacterEvents(params.id)
    }

    data class Parameters(var id: String)
}