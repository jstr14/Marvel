package com.jester.marvel.data.repository.character

import com.jester.marvel.Result
import com.jester.marvel.data.dependencyinjection.qualifier.queries.DefaultQueries
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.character.model.realm.CharacterRealmDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToCharacterDataEntity
import com.jester.marvel.data.repository.character.model.realm.mapToRealmDataEntity
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.datasource.WritableDataSource
import com.jester.marvel.data.repository.query.Query
import com.vicpin.krealmextensions.delete
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import javax.inject.Inject

/**
 * Created by Héctor on 02/11/2017.
 */
class CharacterRealmDataSource @Inject constructor(@DefaultQueries override val queries: MutableSet<Query>)
    : ReadableDataSource<String, CharacterDataEntity>, WritableDataSource<String, CharacterDataEntity> {

    override fun getByKey(key: String): Result<CharacterDataEntity, *> {

        return Result.of { CharacterRealmDataEntity().queryFirst { query -> query.equalTo("id",key)  }?.mapToCharacterDataEntity() }
    }

    override fun addOrUpdate(value: CharacterDataEntity): Result<CharacterDataEntity, *> {

        return Result.of {
            value.mapToRealmDataEntity().save()
            value
        }
    }

    override fun getAll(): Result<Collection<CharacterDataEntity>, *> {

        return Result.of { CharacterRealmDataEntity().queryAll().map { it.mapToCharacterDataEntity() } }
    }

    override fun deleteByKey(key: String): Result<Unit, *> {

        return Result.of { CharacterRealmDataEntity().delete { query -> query.equalTo("id",key) } }
    }


}