package com.jester.marvel.story

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.story.Story
import com.jester.marvel.repository.StoryRepository
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class GetCharacterStoriesInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                        val repository: StoryRepository)
    : Interactor<List<Story>, GetCharacterStoriesInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<List<Story>, *> {
        return repository.getCharacterStories(params.id)
    }

    data class Parameters(var id: String)

}