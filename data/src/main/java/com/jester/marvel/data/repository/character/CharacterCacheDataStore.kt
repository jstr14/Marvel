package com.jester.marvel.data.repository.character

import com.jester.marvel.Result
import com.jester.marvel.data.dependencyinjection.qualifier.DiskCacheTtl
import com.jester.marvel.data.dependencyinjection.qualifier.queries.CharactersCacheQuery
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.datasource.InMemoryCacheDataSource
import com.jester.marvel.data.repository.datasource.TimeProvider
import com.jester.marvel.data.repository.implements
import com.jester.marvel.data.repository.query.Query
import java.lang.Exception
import java.util.*
import javax.inject.Inject

/**
 * Created by Héctor on 17/10/2017.
 */
class CharacterCacheDataStore @Inject constructor(timeProvider: TimeProvider,
                                                  @DiskCacheTtl ttlInMillis: Long,
                                                  @CharactersCacheQuery override val queries: MutableSet<Query>) : InMemoryCacheDataSource<String, CharacterDataEntity>(timeProvider, ttlInMillis, queries) {


    override fun queryAll(query: Class<*>, parameters: HashMap<String, *>?): Result<Collection<CharacterDataEntity>, Exception> {
        queries.forEach { possibleQuery ->
            if (possibleQuery.implements(query)) {
                val result = possibleQuery.queryAll(parameters, items)
                lastItemsUpdate = timeProvider.currentTimeMillis()
                return result as Result<Collection<CharacterDataEntity>, Exception>

            }
        }
        return Result.Failure()
    }

//    override fun deleteByKey(key: String): Result<Unit, Exception> {
//
//        return Result.of {
//            CharacterRealmDataEntity().queryFirst { query -> query.equalTo("id", key) }?.deleteFromRealm()
//            Unit
//        }
//    }
//
//    override fun deleteAll(): Result<Unit, Exception> {
//
//        return Result.of {
//            CharacterRealmDataEntity().deleteAll()
//            lastItemsUpdated = 0
//            Unit
//        }
//    }
//
//    override fun addOrUpdate(value: CharacterDataEntity): Result<CharacterDataEntity, Exception> {
//        return Result.of {
//            value.mapToRealmDataEntity().save()
//            lastItemsUpdated = timeProvider.currentTimeMillis()
//            value
//        }
//    }
//
//    override fun addOrUpdateAll(values: Collection<CharacterDataEntity>): Result<Collection<CharacterDataEntity>, Exception> {
//        return Result.of {
//            values.map { it.mapToRealmDataEntity() }.saveAll()
//            lastItemsUpdated = timeProvider.currentTimeMillis()
//            values
//        }
//    }
//
//    override fun getByKey(key: String): Result<CharacterDataEntity, *> {
//        return Result.of {
//            CharacterRealmDataEntity().queryFirst { query -> query.equalTo("id", key) }?.mapToCharacterDataEntity()
//        }
//
//    }
}