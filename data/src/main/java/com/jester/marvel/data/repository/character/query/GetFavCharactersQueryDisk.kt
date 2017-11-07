package com.jester.marvel.data.repository.character.query

import com.jester.marvel.Result
import com.jester.marvel.data.repository.character.model.realm.CharacterRealmDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToCharacterDataEntity
import com.vicpin.krealmextensions.queryAll
import javax.inject.Inject

/**
 * Created by Héctor on 06/11/2017.
 */
class GetFavCharactersQueryDisk @Inject constructor() : GetFavCharactersQuery {


    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<Any>, Exception> {

        return Result.of { CharacterRealmDataEntity().queryAll().map { it.mapToCharacterDataEntity() } }
    }

}