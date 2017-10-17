package com.jester.marvel.comic

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.comic.Comic
import com.jester.marvel.repository.ComicRepository
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class GetCharacterComicsInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                       val repository: ComicRepository)
    : Interactor<List<Comic>, GetCharacterComicsInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<List<Comic>, *> {
        return repository.getCharacterComics(params.id)
    }

    data class Parameters(var id: String)
}