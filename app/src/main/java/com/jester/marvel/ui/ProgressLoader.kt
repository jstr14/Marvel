package com.jester.marvel.ui

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.View
import com.jester.marvel.R
import io.saeid.fabloading.LoadingView
import javax.inject.Inject

/**
 * Created by Héctor on 11/10/2017.
 */
class ProgressLoader @Inject constructor() {

     fun addImagesToProgressLoader(progressLoader: LoadingView, context: Context){

        progressLoader.addAnimation(Color.parseColor("#FFD200"), R.drawable.marvel_1,
                LoadingView.FROM_LEFT)
        progressLoader.addAnimation(Color.parseColor("#2F5DA9"), R.drawable.marvel_2,
                LoadingView.FROM_TOP)
        progressLoader.addAnimation(Color.parseColor("#FF4218"), R.drawable.marvel_3,
                LoadingView.FROM_RIGHT)
        progressLoader.addAnimation(Color.parseColor("#C7E7FB"), R.drawable.marvel_4,
                LoadingView.FROM_BOTTOM)

         progressLoader.repeat = 4
        progressLoader.visibility = View.VISIBLE

         val mHandler = Handler()
         progressLoader.addListener(object : LoadingView.LoadingListener {
             override fun onAnimationStart(currentItemPosition: Int) {
             }

             override fun onAnimationRepeat(nextItemPosition: Int) {
                 mHandler.postDelayed({ progressLoader.startAnimation() }, 50)
             }

             override fun onAnimationEnd(nextItemPosition: Int) {
             }
         })
        progressLoader.startAnimation()
    }


    private fun startInfiniteAnimation(progressLoader: LoadingView){

        val mHandler = Handler()
        mHandler.postDelayed({ progressLoader.startAnimation() }, 50)
    }

    fun pauseAnimation(progressLoader: LoadingView){

        progressLoader.pauseAnimation()
        progressLoader.visibility = View.GONE
    }

}