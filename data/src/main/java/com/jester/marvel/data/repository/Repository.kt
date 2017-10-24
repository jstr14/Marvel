package com.jester.marvel.data.repository

import com.jester.marvel.Result
import com.jester.marvel.data.repository.datasource.CacheDataSource
import com.jester.marvel.data.repository.datasource.ReadableDataSource
import com.jester.marvel.data.repository.datasource.WritableDataSource
import com.jester.marvel.data.repository.policy.ReadPolicy
import com.jester.marvel.data.repository.policy.WritePolicy

/**
 * Created by Borja on 27/2/17.
 */
open class Repository<Key, Value> {

    val writableDataSources = ArrayList<WritableDataSource<Key, Value>>()
    val readableDataSources = ArrayList<ReadableDataSource<Key, Value>>()
    val cacheDataSources = ArrayList<CacheDataSource<Key, Value>>()

    fun getByKey(key: Key, policy: ReadPolicy = ReadPolicy.READ_ALL): Result<Value, Exception> {
        var result: Result<Value, Exception> = Result.Failure()

        if (policy.useCache()) {
            result = getValueFromCacheDataSources(key)

        }
        result.failure {
            if (policy.useReadable()) {
                result = getValueFromReadableDataSources(key)
            }
        }

        result.success {
            result ->
            populateCaches(result)
        }

        return result
    }

    fun getAll(policy: ReadPolicy = ReadPolicy.READ_ALL): Result<Collection<Value>, *> {
        var result: Result<Collection<Value>, *> = Result.Failure()

        if (policy.useCache()) {
            result = getValuesFromCacheDataSources()

        }

        result.failure {
            if (policy.useReadable()) {
                result = getValuesFromReadableDataSources()
            }
        }

        result.success {
            value ->
            populateCaches(value)
        }

        return result
    }

    fun addOrUpdate(value: Value, policy: WritePolicy = WritePolicy.WRITE_ALL): Result<Value, *> {

        var result: Result<Value, Exception> = Result.Failure()

        writableDataSources.forEach {
            writableDataSource ->
            result = writableDataSource.addOrUpdate(value)
        }

        result.success {
            value ->
            populateCaches(value)
        }

        if (policy.writeCache()) {
            populateCaches(value)
        }

        return result
    }

    fun addOrUpdateAll(values: Collection<Value>): Result<Collection<Value>, *> {
        var result: Result<Collection<Value>, *> = Result.Failure()

        writableDataSources.forEach {
            writableDataSource ->
            result = writableDataSource.addOrUpdateAll(values)
        }

        result.success {
            value ->
            populateCaches(value)
        }

        return result
    }

    fun deleteByKey(key: Key): Result<Unit, *> {
        var result: Result<Unit, Exception> = Result.Failure()

        writableDataSources.forEach {
            writableDataSource ->
            result = writableDataSource.deleteByKey(key)
        }
        cacheDataSources.forEach {
            cacheDataSource ->
            result = cacheDataSource.deleteByKey(key)
        }

        return result
    }

    fun deleteAll(): Result<Unit, *> {
        var result: Result<Unit, *> = Result.Failure()

        writableDataSources.forEach {
            writableDataSource ->
            result = writableDataSource.deleteAll()
        }
        cacheDataSources.forEach {
            cacheDataSource ->
            result = cacheDataSource.deleteAll()
        }
        return result
    }

    fun query(query: Class<*>, parameters: HashMap<String, *>? = null, policy: ReadPolicy = ReadPolicy.READ_ALL): Result<Value, *> {
        var result: Result<Value, *> = Result.Failure()
        if (policy.useCache()) {
            for (cacheDataSource in cacheDataSources) {
                result = cacheDataSource.query(query, parameters)
                result.success {
                    value ->
                    if (isValidValue(value,cacheDataSource)) {
                        return result
                    } else {
                        cacheDataSource.deleteAll()
                        result = Result.Failure()
                    }
                }
            }
        }

        result.failure {
            if (policy.useReadable()) {
                for (readableDataSource in readableDataSources) {
                    result = readableDataSource.query(query, parameters)
                    if (result.isSuccess()) {
                        break
                    }

                }
            }
        }

        result.success {
            value ->
            populateCaches(value)
        }

        return result
    }

    fun queryAll(query: Class<*>, parameters: HashMap<String, *>? = null, policy: ReadPolicy = ReadPolicy.READ_ALL): Result<Collection<Value>, *> {
        var result: Result<Collection<Value>, *> = Result.Failure()
        if (policy.useCache()) {
            for (cacheDataSource in cacheDataSources) {
                result = cacheDataSource.queryAll(query, parameters)
                result.success {
                    value ->
                    if (areValidValues(value,cacheDataSource)) {
                        return result
                    } else {
                        cacheDataSource.deleteAll()
                        result = Result.Failure()
                    }
                }
            }
        }

        result.failure {
            if (policy.useReadable()) {
                for (readableDataSource in readableDataSources) {
                    result = readableDataSource.queryAll(query, parameters)
                    if (result.isSuccess()) {
                        break
                    }

                }
            }
        }

        result.success {
            value ->
            populateCaches(value)
        }

        return result
    }

    private fun populateCaches(value: Value) {
        cacheDataSources.forEach {
            cacheDataSource ->

            cacheDataSource.addOrUpdate(value)
        }
    }

    private fun populateCaches(values: Collection<Value>) {
        cacheDataSources.forEach {
            cacheDataSource ->

            cacheDataSource.addOrUpdateAll(values)
        }
    }

    private fun getValuesFromCacheDataSources(): Result<Collection<Value>, *> {
        var result: Result<Collection<Value>, *> = Result.Failure()

        cacheDataSources.forEach {
            cacheDataSource ->

            result = cacheDataSource.getAll()
            result.success {
                value ->
                if (areValidValues(value, cacheDataSource)) {
                    return result
                } else {
                    cacheDataSource.deleteAll()
                    result = Result.Failure()
                }
            }
        }
        return result
    }

    private fun getValueFromReadableDataSources(key: Key): Result<Value, *> {
        var result: Result<Value, *> = Result.Failure()
        readableDataSources.forEach {
            readableDataSource ->
            result = readableDataSource.getByKey(key)
            result.success {
                return result
            }
        }
        return result
    }

    private fun getValueFromCacheDataSources(key: Key): Result<Value, *> {
        var result: Result<Value, *> = Result.Failure()
        cacheDataSources.forEach {
            cacheDataSource ->

            result = cacheDataSource.getByKey(key)
            result.success {
                value ->
                if (cacheDataSource.isValid(value)) {
                    return result
                } else {
                    cacheDataSource.deleteByKey(key)
                    result = Result.Failure()
                }
            }

        }
        return result
    }

    private fun getValuesFromReadableDataSources(): Result<Collection<Value>, *> {
        var result: Result<Collection<Value>, *> = Result.Failure()
        readableDataSources.forEach {
            readableDataSource ->
            result = readableDataSource.getAll()
            result.success {
                return result
            }
        }
        return result
    }

    private fun areValidValues(values: Collection<Value>?, cacheDataSource: CacheDataSource<Key, Value>): Boolean {
        var areValidValues = false
        values?.forEach {
            value ->
            areValidValues = areValidValues or isValidValue(value,cacheDataSource)
        }
        return areValidValues
    }

    private fun isValidValue(value: Value, cacheDataSource: CacheDataSource<Key, Value>): Boolean {

         return cacheDataSource.isValid(value)

    }

}