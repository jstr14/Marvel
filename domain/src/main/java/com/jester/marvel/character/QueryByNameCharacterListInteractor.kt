package com.jester.marvel.character

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.character.Character
import com.jester.marvel.repository.CharacterRepository
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class QueryByNameCharacterListInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                             val repository: CharacterRepository)
    : Interactor<List<Character>, QueryByNameCharacterListInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<List<Character>, *> {
        return repository.getCharactersByQueryName(params.offset,params.queryName)
    }

    data class Parameters(var offset: Int, var queryName: String)
}