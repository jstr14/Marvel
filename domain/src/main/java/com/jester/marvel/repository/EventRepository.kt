package com.jester.marvel.repository

import com.jester.marvel.Result
import com.jester.marvel.model.event.Event

/**
 * Created by Héctor on 16/10/2017.
 */
interface EventRepository {

    fun getCharacterEvents(id:String): Result<List<Event>, Exception>
}