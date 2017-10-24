package com.jester.marvel.data.repository.datasource

import com.jester.marvel.Result
import com.jester.marvel.data.repository.implements
import com.jester.marvel.data.repository.query.Query
import java.util.*

/**
 * A simple and generic in-memory cache ready to use in your repositories.
 */
open class InMemoryCacheDataSource<Key, Value : Identifiable<Key>>(val timeProvider: TimeProvider, val ttlInMillis: Long, override val queries: MutableSet<Query>) : CacheDataSource<Key, Value> {

    var lastItemsUpdate: Long = 0
    val items: MutableMap<Key, Value> = LinkedHashMap()

    override fun getByKey(key: Key): Result<Value, kotlin.Exception> {
        return Result.of { items[key] }
    }

    override fun getAll(): Result<Collection<Value>, Exception> {
        return Result.of { ArrayList(items.values) }
    }

    override fun addOrUpdate(value: Value): Result<Value, Exception> {
        return Result.of {
            synchronized(this) {
                items.put(value.getKey(), value)
                lastItemsUpdate = timeProvider.currentTimeMillis()
                value
            }
        }
    }

    override fun addOrUpdateAll(values: Collection<Value>): Result<Collection<Value>, Exception> {
        return Result.of {
            synchronized(this) {
                values.forEach { value ->
                    addOrUpdate(value)
                }

                values
            }
        }
    }

    override fun queryAll(query: Class<*>, parameters: HashMap<String, *>?): Result<Collection<Value>, Exception> {
        queries.forEach { possibleQuery ->
            if (possibleQuery.implements(query)) {
                val result = possibleQuery.queryAll(parameters, items) as Result<Collection<Value>,Exception>
                lastItemsUpdate = timeProvider.currentTimeMillis()
                return result
            }
        }
        return Result.Failure()
    }

    override fun deleteByKey(key: Key): Result<Unit, Exception> {
        return Result.of {
            items.remove(key)
            Unit
        }
    }

    override fun deleteAll(): Result<Unit, Exception> {
        return Result.of {
            items.clear()
            lastItemsUpdate = 0
        }
    }

    override fun isValid(value: Value): Boolean {
        return timeProvider.currentTimeMillis() - lastItemsUpdate < ttlInMillis
    }
}