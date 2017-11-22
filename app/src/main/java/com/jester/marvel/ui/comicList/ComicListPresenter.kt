package com.jester.marvel.ui.comicList

import com.jester.marvel.comic.GetComicsListInteractor
import com.jester.marvel.ui.exception.AndroidExceptionHandler
import com.jester.marvel.ui.model.mapper.mapToComicViewEntity
import javax.inject.Inject

/**
 * Created by Héctor on 22/11/2017.
 */
class ComicListPresenter @Inject constructor(val view: ComicListView, val getComicsListInteractor: GetComicsListInteractor,
                                             val exceptionHandler: AndroidExceptionHandler) {

    fun onCloseSearchView() {

    }

    fun onSearchQueryChange(newText: String) {

    }

    fun onStart() {

        getComicsListInteractor.execute(GetComicsListInteractor.Parameters(0,"")){
            result ->
            result.success { comics ->
                comics.size
                view.showComics(comics.map { it.mapToComicViewEntity() })
            }
            result.failure { exception ->
                exceptionHandler.notifyException(view, exception)

            }
        }
    }


}