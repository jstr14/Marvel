package com.jester.marvel.ui.exception

import com.jester.marvel.ui.base.BaseView

/**
 * Created by Borja on 23/5/17.
 */
interface ExceptionHandler {
    fun <T : BaseView> notifyException(view: T, exception: Exception?)
}