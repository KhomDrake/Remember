package com.remember.remember.ui.home.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.remember.repository.model.MemoryLineType

class HomePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val types: List<MemoryLineType>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = types.count()

    override fun createFragment(position: Int) = MemoryLinesHomeFragment(
        types[position].idType,
        types[position].name
    )
}
