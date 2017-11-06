package com.jester.marvel.ui

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View



/**
 * Created by Héctor on 24/10/2017.
 */
class HorizontalSpaceItemDecorator(val commonBottonSpace: Int,
                                   val context: Context,
                                   val handleLastItem: Boolean = false,
                                   val lastItemSpaceRight: Int = 0): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {

        if (handleLastItem && parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1)
            outRect.right = convertDpToPx(lastItemSpaceRight)
        else
            outRect.right = convertDpToPx(commonBottonSpace)
    }

    private fun convertDpToPx(dp: Int): Int {

        val metrics = context.resources.displayMetrics
        return Math.round(dp * (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

    }
}