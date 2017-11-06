package com.jester.marvel.data.repository.comic

import com.jester.marvel.Result
import com.jester.marvel.data.repository.Repository
import com.jester.marvel.data.repository.comic.model.ComicDataEntity
import com.jester.marvel.data.repository.comic.model.mapToComic
import com.jester.marvel.data.repository.comic.query.GetComicsListQuery
import com.jester.marvel.model.comic.Comic
import com.jester.marvel.repository.ComicRepository
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class ComicDataRepository @Inject constructor(comicApiDataSource: ComicApiDataSource)
    : ComicRepository, Repository<String, ComicDataEntity>() {
    init {
        readableDataSources.add(comicApiDataSource)
    }

    override fun getCharacterComics(id: String): Result<List<Comic>, Exception> {

        val params = HashMap<String, Any>()
        params.put(GetComicsListQuery.ID, id)
        val result = queryAll(GetComicsListQuery::class.java, params)
        return result.map { it.map { it.mapToComic() } }    }
}