package com.jester.marvel.data.repository.story

import com.jester.marvel.data.dependencyinjection.qualifier.StoryApiQuery
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.query.Query
import com.jester.marvel.data.repository.story.model.StoryDataEntity
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class StoryApiDataSource @Inject constructor(@StoryApiQuery override val queries: MutableSet<Query>)
    : ReadableDataSource<String, StoryDataEntity> {
}