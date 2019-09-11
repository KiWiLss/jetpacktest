package com.kiwilss.lxkj.mvvmtest.utils

import android.graphics.Rect
import android.view.View

class SpaceItemDecoration(space: Int) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    private val mSpace = space

    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 0
        outRect.bottom = mSpace
    }

//    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        outRect?.top = 0
//        outRect?.bottom = mSpace
//    }
}