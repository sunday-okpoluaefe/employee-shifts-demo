package com.sunday.shiftsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.sunday.shiftsapplication.data.model.Shift
import com.sunday.shiftsapplication.databinding.LayoutShiftItemBinding
import com.sunday.shiftsapplication.utils.formatDate
import com.sunday.shiftsapplication.utils.formatTime


class ShiftViewHolder(private val binding: LayoutShiftItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(shift: Shift){
        shift.name.also { binding.name.text = it }
        "(${shift.role})".also { binding.role.text = it }
        binding.date.text = shift.startDate.formatDate()
        binding.time.text = shift.startDate.formatTime()

        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.indicator.drawable),
            Colors.valueOf(shift.color.uppercase()).color
        )
    }

    companion object {
        fun from(parent: ViewGroup): ShiftViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LayoutShiftItemBinding.inflate(inflater, parent, false)
            return ShiftViewHolder(binding)
        }
    }
}

enum class Colors(val color: Int) {
    RED(-65536),
    GREEN(-16711936),
    BLUE(-16776961)
}