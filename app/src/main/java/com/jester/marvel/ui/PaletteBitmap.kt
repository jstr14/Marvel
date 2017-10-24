package com.jester.marvel.ui

import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.NonNull
import android.support.v7.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.bumptech.glide.util.Util


/**
 * Created by Héctor on 20/10/2017.
 */
class PaletteBitmap(@param:NonNull val bitmap: Bitmap, @param:NonNull val palette: Palette)

class PaletteBitmapResource(@param:NonNull private val paletteBitmap: PaletteBitmap, @param:NonNull private val bitmapPool: BitmapPool) : Resource<PaletteBitmap> {

    override fun getResourceClass(): Class<PaletteBitmap> {
        return paletteBitmap.javaClass
    }

    override fun getSize(): Int {
       return Util.getBitmapByteSize(paletteBitmap.bitmap)
    }


    override fun get(): PaletteBitmap {
        return paletteBitmap
    }

    override fun recycle() {
        bitmapPool.put(paletteBitmap.bitmap)
        paletteBitmap.bitmap.recycle()

    }


}

class PaletteBitmapTranscoder(@NonNull context: Context) : ResourceTranscoder<Bitmap, PaletteBitmap> {
    private val bitmapPool: BitmapPool

    val id: String
        get() = PaletteBitmapTranscoder::class.java.name

    init {
        this.bitmapPool = Glide.get(context).bitmapPool
    }

    override fun transcode(toTranscode: Resource<Bitmap>?, options: Options?): Resource<PaletteBitmap> {
        val bitmap = toTranscode!!.get()
        val palette = Palette.Builder(bitmap).generate()
        val result = PaletteBitmap(bitmap, palette)
        return PaletteBitmapResource(result, bitmapPool)
    }


}