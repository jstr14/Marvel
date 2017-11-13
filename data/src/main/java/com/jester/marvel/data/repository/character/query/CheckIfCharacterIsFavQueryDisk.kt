package com.jester.marvel.data.repository.character.query

import com.jester.marvel.Result
import com.jester.marvel.data.repository.character.model.realm.CharacterRealmDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToCharacterDataEntity
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.queryFirst
import javax.inject.Inject

/**
 * Created by Héctor on 10/11/2017.
 */
class CheckIfCharacterIsFavQueryDisk @Inject constructor() : CheckIfCharacterIsFavQuery {


    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<Any>, Exception> {

        return Result.of { CharacterRealmDataEntity().queryAll().map { it.mapToCharacterDataEntity() } }
    }

    override fun query(parameters: HashMap<String, *>?, queryable: Any?): Result<Any, Exception> {

        val id = parameters?.get(CheckIfCharacterIsFavQuery.Parameters.ID) as String
        val result = CharacterRealmDataEntity().
                queryFirst { realmQuery -> realmQuery.equalTo("id", id) }?.mapToCharacterDataEntity()

        return Result.of { result}
    }

}