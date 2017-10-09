package com.jester.marvel

import android.os.Handler
import android.os.Looper
import com.jester.marvel.async.PostExecutionThread

/**
 * Created by Borja on 3/1/17.
 */
class UiThread constructor(val handler: Handler = Handler(Looper.getMainLooper())) : PostExecutionThread
{
    override fun <T> submit(function: () -> T?) {
        handler.post {
            function.invoke()
        }
    }

}