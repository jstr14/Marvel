package com.jester.marvel.character

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.repository.CharacterRepository
import javax.inject.Inject

/**
 * Created by Héctor on 03/11/2017.
 */
class RemoveFabCharacterInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                       val repository: CharacterRepository)
    : Interactor<Unit, RemoveFabCharacterInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<Unit, Exception> {
        return repository.removeCharacter(params.id)
    }

    data class Parameters(var id: String)
}