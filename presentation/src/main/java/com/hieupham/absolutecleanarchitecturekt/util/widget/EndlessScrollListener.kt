package com.hieupham.absolutecleanarchitecturekt.util.widget

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


class EndlessScrollListener(
        private val listener: LoadMoreListener) : RecyclerView.OnScrollListener() {

    companion object {
        private const val VISIBLE_THRESHOLD = 3
    }

    private var totalPreviousItem = 0
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var isLoading = true

    interface LoadMoreListener {
        fun onLoadMore()
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(view, dx, dy)
        visibleItemCount = view.childCount
        totalItemCount = view.layoutManager.itemCount

        firstVisibleItem = when {
            view.layoutManager is LinearLayoutManager -> (view.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            view.layoutManager is GridLayoutManager -> (view.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            else -> throw RuntimeException("Un support this kind of LayoutManager ")
        }

        if (isLoading && totalItemCount > totalPreviousItem) {
            isLoading = false
            totalPreviousItem = totalItemCount
        }
        if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            isLoading = true
            listener.onLoadMore()
        }
    }

    fun reset() {
        isLoading = true
        firstVisibleItem = 0
        totalItemCount = 0
        visibleItemCount = 0
        totalPreviousItem = 0
    }

}