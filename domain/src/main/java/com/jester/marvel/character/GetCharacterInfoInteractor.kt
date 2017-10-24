package com.jester.marvel.character

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.comic.GetCharacterComicsInteractor
import com.jester.marvel.event.GetCharacterEventsInteractor
import com.jester.marvel.flatMap
import com.jester.marvel.interactor.Interactor
import com.jester.marvel.model.character.Character
import com.jester.marvel.model.comic.Comic
import com.jester.marvel.model.event.Event
import com.jester.marvel.model.story.Story
import com.jester.marvel.story.GetCharacterStoriesInteractor
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class GetCharacterInfoInteractor @Inject constructor(postExecutionThread: PostExecutionThread,
                                                     private val getCharacterComicsInteractor: GetCharacterComicsInteractor,
                                                     private val getCharacterEventsInteractor: GetCharacterEventsInteractor,
                                                     private val getCharacterStoriesInteractor: GetCharacterStoriesInteractor)
    : Interactor<Character, GetCharacterInfoInteractor.Parameters>(postExecutionThread) {

    override fun run(params: Parameters): Result<Character, *> {

        val result = this.getCharacterComics(params.character.id).map { value ->
            params.character.comics = value
            params.character
        }.flatMap {
            getCharacterEvents(params.character.id)
        }.map {
            value ->
            params.character.events = value
            params.character
        }.flatMap {
            getCharacterStories(params.character.id)
        }.map {
            value ->
            params.character.stories = value
            params.character
        }

        return result

    }

    data class Parameters(var character: Character)

    private fun getCharacterComics(id: String): Result<List<Comic>, Exception> {

        return getCharacterComicsInteractor.run(GetCharacterComicsInteractor.Parameters(id))
    }

    private fun getCharacterEvents(id: String): Result<List<Event>, Exception> {

        return getCharacterEventsInteractor.run(GetCharacterEventsInteractor.Parameters(id))
    }

    private fun getCharacterStories(id: String): Result<List<Story>, Exception> {

        return getCharacterStoriesInteractor.run(GetCharacterStoriesInteractor.Parameters(id))
    }

}