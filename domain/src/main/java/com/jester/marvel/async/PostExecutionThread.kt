package com.jester.marvel.async

/**
 * Created by Borja on 3/1/17.
 */
interface PostExecutionThread {

    fun <T> submit(function: () -> T?)
}