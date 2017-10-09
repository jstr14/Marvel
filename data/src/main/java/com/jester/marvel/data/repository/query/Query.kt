package com.jester.marvel.data.repository.query

import com.jester.marvel.Result

/**
 * Created by Borja on 21/3/17.
 */
interface Query {

    fun queryAll(parameters: HashMap<String, *>? = null, queryable: Any? = null): Result<Collection<*>, *> {
        return Result.Failure()
    }
    fun query(parameters: HashMap<String, *>? = null, queryable: Any? = null): Result<*, *> {
        return Result.Failure()
    }

}