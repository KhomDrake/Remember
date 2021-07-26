package com.remember.test.matchers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class RecyclerViewItemViewMatcher(
    private val recyclerViewId: Int,
    private val position: Int,
    private val itemViewId: Int
) : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        description?.appendValue("Not found recyclerview with id $recyclerViewId")
    }

    override fun matchesSafely(item: View): Boolean {
        val recyclerView = (item as? RecyclerView) ?: return false

        val view = recyclerView.findViewHolderForAdapterPosition(position)?.itemView ?: return false

        view.findViewById<View?>(itemViewId) ?: return false
        return true
    }
}

class RecyclerViewMatcherQuantityItems(
    private val quantityOfItems: Int
) : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        description?.appendValue("Not found recyclerview with quantity of items equal to $quantityOfItems")
    }

    override fun matchesSafely(item: View): Boolean {
        val recyclerView = (item as? RecyclerView) ?: return false

        return recyclerView.adapter?.itemCount == quantityOfItems
    }
}