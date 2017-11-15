package com.jester.marvel.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.jester.marvel.R
import com.jester.marvel.dependencyinjection.application.GlideApp




/**
 * Created by Héctor on 13/10/2017.
 */
fun ImageView.load(url: String) {

    GlideApp.with(this)
            .load(url)
            .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
            .error(R.color.grey)
            .into(this)

}

fun ImageView.loadAndPalette(url:String,view: View){

    val palette = GlidePalette.with(url)

    Glide.with(this).load(url)
            .listener(palette
                    .use(BitmapPalette.Profile.VIBRANT)
                    .intoBackground(view)
            )
    .into(this)
}

fun ImageView.getBitmap(): Bitmap{
    this.buildDrawingCache()
    return this.drawingCache
}

fun Toolbar.getToolbarNavigationButton() : View? {

    val size = this.getChildCount()
    for (i in 0 until size) {
        val child = this.getChildAt(i)
        if (child is ImageButton) {
            val btn = child as ImageButton
            if (btn.drawable === this.getNavigationIcon()) {
                return btn
            }
        }
    }
    return null
}

fun TextView.setPrefixTextBold(prefix: String, text: String, separator: String = " ") {

    val styledText = SpannableString(prefix)
    styledText.setSpan(StyleSpan(Typeface.BOLD),0,prefix.length,0)
    this.append(styledText)
    this.append(separator)
    this.append(text)

}

fun Int.getContrastVersionForColor(): Int {
    val hsv = FloatArray(3)
    Color.RGBToHSV(Color.red(this), Color.green(this), Color.blue(this),
            hsv)
    if (hsv[2] < 0.5) {
        hsv[2] = 0.7f
    } else {
        hsv[2] = 0.3f
    }
    hsv[1] = hsv[1] * 0.2f
    return Color.HSVToColor(hsv)
}

