package com.jester.marvel.interactor

import com.jester.marvel.Result
import com.jester.marvel.async.PostExecutionThread
import com.jester.marvel.async.doAsync
import com.jester.marvel.async.onComplete

/**
 * Created by Borja on 1/6/17.
 */
abstract class Interactor<out SuccessValue, in Parameters > constructor(val postExecutionThread: PostExecutionThread) {

    fun execute(parameters: Parameters, delegate: (result: Result<SuccessValue, *>) -> Unit) = doAsync {
        val result = run(parameters)

        onComplete(postExecutionThread) {
            delegate(result)
        }
    }

    abstract fun run(params: Parameters): Result<SuccessValue, *>
}