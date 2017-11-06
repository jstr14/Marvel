package com.jester.marvel.repository

import com.jester.marvel.Result
import com.jester.marvel.model.story.Story

/**
 * Created by Héctor on 16/10/2017.
 */
interface StoryRepository {

    fun getCharacterStories(id:String): Result<List<Story>, Exception>

}