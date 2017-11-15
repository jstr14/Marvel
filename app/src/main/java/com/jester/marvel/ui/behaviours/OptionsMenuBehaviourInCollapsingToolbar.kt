package com.jester.marvel.ui.behaviours

import android.support.design.widget.AppBarLayout

/**
 * Created by Héctor on 13/11/2017.
 */
interface OptionsMenuBehaviourInCollapsingToolbar : AppBarLayout.OnOffsetChangedListener {

    companion object {

        private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
        private var optionIsVisible = false

    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {

        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()

        handleItemOptionVisibility(percentage)

    }

    private fun handleItemOptionVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!optionIsVisible) {
                showOptionBehaviour()
                optionIsVisible = true
            }
        } else {

            if (optionIsVisible) {
                hideOptionBehaviour()
                optionIsVisible = false
            }
        }
    }


    fun showOptionBehaviour()

    fun hideOptionBehaviour()


}