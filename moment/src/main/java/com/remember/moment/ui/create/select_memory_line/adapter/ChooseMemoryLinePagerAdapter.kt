package com.remember.moment.ui.create.select_memory_line.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.remember.repository.model.MemoryLineType
import java.io.File

class ChooseMemoryLinePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val file: File,
    private val types: List<MemoryLineType>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = types.count()

    override fun createFragment(position: Int) = ChooseMemoryLinesFragment(
        file,
        types[position].idType,
        types[position].name,
        withImages = false
    )
}
