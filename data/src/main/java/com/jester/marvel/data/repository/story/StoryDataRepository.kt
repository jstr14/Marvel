package com.jester.marvel.data.repository.story

import com.jester.marvel.Result
import com.jester.marvel.data.repository.Repository
import com.jester.marvel.data.repository.story.model.StoryDataEntity
import com.jester.marvel.data.repository.story.model.mapToStory
import com.jester.marvel.data.repository.story.query.GetStoriesListQuery
import com.jester.marvel.model.story.Story
import com.jester.marvel.repository.StoryRepository
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class StoryDataRepository @Inject constructor(storyApiDataSource: StoryApiDataSource)
    : StoryRepository, Repository<String, StoryDataEntity>() {
    init {
        readableDataSources.add(storyApiDataSource)
    }

    override fun getCharacterStories(id: String): Result<List<Story>, Exception> {

        val params = HashMap<String, Any>()
        params.put(GetStoriesListQuery.ID, id)
        val result = queryAll(GetStoriesListQuery::class.java, params)
        return result.map { it.map { it.mapToStory() } }
    }
}
