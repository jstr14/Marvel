package com.jester.marvel.data.repository.datasource

import com.jester.marvel.Result
import com.jester.marvel.data.repository.implements
import com.jester.marvel.data.repository.query.Query

/**
 * Created by Borja on 6/3/17.
 */
interface CacheDataSource<Key, Value> : ReadableDataSource<Key, Value>, WritableDataSource<Key, Value> {

    fun isValid(value: Value): Boolean
}

interface WritableDataSource<Key, Value> {

    fun deleteByKey(key: Key): Result<Unit, *>

    fun deleteAll(): Result<Unit, *>

    fun addOrUpdate(value: Value): Result<Value, *>

    fun addOrUpdateAll(values: Collection<Value>): Result<Collection<Value>, *>
}

interface ReadableDataSource<Key, out Value> {

    val queries: MutableSet<Query>

    fun getByKey(key: Key): Result<Value, *> {
        return Result.Failure()
    }

    fun getAll(): Result<Collection<Value>, *> {
        return Result.Failure()
    }

    fun queryAll(query: Class<*>, parameters: HashMap<String, *>? = null): Result<Collection<Value>, *> {
        queries.forEach {
            possibleQuery ->
            if (possibleQuery.implements(query)) {
                return possibleQuery.queryAll(parameters) as Result<Collection<Value>, *>
            }
        }
        return Result.Failure()
    }

    fun query(query: Class<*>, parameters: HashMap<String, *>? = null): Result<Value, *> {
        queries.forEach {
            possibleQuery ->
            if (possibleQuery.implements(query)) {
                return possibleQuery.query(parameters) as Result<Value, *>
            }
        }
        return Result.Failure()
    }

}
