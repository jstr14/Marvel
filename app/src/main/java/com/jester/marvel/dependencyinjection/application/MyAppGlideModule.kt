package com.jester.marvel.dependencyinjection.application

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.module.LibraryGlideModule

/**
 * Created by Héctor on 13/10/2017.
 */
@GlideModule
class MyAppGlideModule : AppGlideModule()

@GlideModule
class MyLibraryGlideModule : LibraryGlideModule()
