package com.jester.marvel.character

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.character.Character
import com.jester.marvel.repository.CharacterRepository
import javax.inject.Inject

/**
 * Created by Héctor on 06/11/2017.
 */
class GetFavCharactersInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                     val repository: CharacterRepository)
    : Interactor<List<Character>, Unit>(postExecutionThread) {

    override fun run(params: Unit): Result<List<Character>, Exception> {
        return repository.getFavCharacters()
    }

}