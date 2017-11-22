package com.jester.marvel.comic

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.comic.Comic
import com.jester.marvel.repository.ComicRepository
import javax.inject.Inject

/**
 * Created by Héctor on 22/11/2017.
 */
class GetComicsListInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                  val repository: ComicRepository)
    : Interactor<List<Comic>, GetComicsListInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<List<Comic>, *> {
        return repository.getCharactersList(params.offset, params.queryName)
    }

    data class Parameters(val offset: Int, val queryName: String)
}