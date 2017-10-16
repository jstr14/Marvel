package com.jester.marvel.data.repository.character

import com.jester.marvel.data.dependencyinjection.qualifier.CharactersApiQuery
import com.jester.marvel.data.network.ConnectionChecker
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.query.Query
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class CharacterApiDataSource @Inject constructor(@CharactersApiQuery override val queries: MutableSet<Query>,
                                                 val connectionChecker: ConnectionChecker)
    : ReadableDataSource<String, CharacterDataEntity> {


}