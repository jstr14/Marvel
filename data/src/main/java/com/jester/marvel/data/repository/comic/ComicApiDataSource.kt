package com.jester.marvel.data.repository.comic

import com.jester.marvel.data.dependencyinjection.qualifier.ComicsApiQuery
import com.jester.marvel.data.repository.comic.model.ComicDataEntity
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.query.Query
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class ComicApiDataSource @Inject constructor(@ComicsApiQuery override val queries: MutableSet<Query>)
    : ReadableDataSource<String, ComicDataEntity> {

}