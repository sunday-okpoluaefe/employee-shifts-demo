package com.sunday.shiftsapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sunday.shiftsapplication.data.model.Shift

class ShiftsAdapter : RecyclerView.Adapter<ShiftViewHolder>(){

    private var data: List<Shift> = ArrayList()

    fun load(shifts: List<Shift>) {
        val list = data
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ShiftAdapterDiffUtil(
                list,
                shifts
            )
        )

        data = shifts
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder =
        ShiftViewHolder.from(parent)

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}