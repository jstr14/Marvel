package com.jester.marvel.character

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.character.Character
import com.jester.marvel.repository.CharacterRepository
import javax.inject.Inject

/**
 * Created by Héctor on 02/11/2017.
 */
class SaveFabCharacterRealmInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                          val repository: CharacterRepository)
    : Interactor<Character, SaveFabCharacterRealmInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<Character, Exception> {
        return repository.saveCharacter(params.character)
    }

    data class Parameters(var character: Character)
}