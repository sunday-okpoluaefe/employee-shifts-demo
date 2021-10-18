package com.sunday.shiftsapplication.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sunday.shiftsapplication.data.model.Shift

class ShiftAdapterDiffUtil(private var old: List<Shift>, private var new: List<Shift>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = new[newItemPosition].name == old[oldItemPosition].name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = new[newItemPosition] == old[oldItemPosition]
}