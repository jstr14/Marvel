package com.jester.marvel.ui

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View

/**
 * Created by Héctor on 24/10/2017.
 */
class VerticalSpaceItemDecorator(val commonBottonSpace: Int,
                                 val context: Context,
                                 val handleFirstItem: Boolean = false,
                                 val firstItemSpace: Int = 0,
                                 val handleLastItem: Boolean = false,
                                 val lastItemSpace: Int = 0): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {

        if (handleFirstItem && parent.getChildAdapterPosition(view) == 0){
            outRect.top = convertDpToPx(firstItemSpace)
        }

        if (handleLastItem && parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1) outRect.bottom = convertDpToPx(lastItemSpace)
        else
            outRect.bottom = convertDpToPx(commonBottonSpace)
    }

    private fun convertDpToPx(dp: Int): Int {

        val metrics = context.resources.displayMetrics
        return Math.round(dp * (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

    }
}