package com.jester.marvel.character

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.character.Character
import com.jester.marvel.repository.CharacterRepository
import javax.inject.Inject

/**
 * Created by Héctor on 18/10/2017.
 */
class GetCharacterByIdInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                     val repository: CharacterRepository)
    : Interactor<Character, GetCharacterByIdInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<Character, *> {
        return repository.getCharacterById(params.id)
    }

    data class Parameters(var id: String)
}