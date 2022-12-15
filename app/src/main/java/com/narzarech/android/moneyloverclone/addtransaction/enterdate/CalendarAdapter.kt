package com.narzarech.android.moneyloverclone.addtransaction.enterdate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.narzarech.android.moneyloverclone.databinding.CalendarCellBinding

class CalendarAdapter(private val clickListener: CalendarCellListener) : ListAdapter<String, CalendarAdapter.CalendarViewHolder>(DiffCallback) {
    class CalendarViewHolder private constructor(val binding: CalendarCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CalendarViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CalendarCellBinding.inflate(layoutInflater, parent, false)
                binding.root.layoutParams.height = (parent.height * 0.166666666).toInt()
                return CalendarViewHolder(binding)
            }
        }

        fun bind(item: String, clickListener: CalendarCellListener) {
            binding.day = item
            binding.clickListener = clickListener
            binding.cellText.text = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val newItem = getItem(position)
        holder.bind(newItem, clickListener)
    }
}

class CalendarCellListener(val clickListener: (String) -> Unit) {
    fun onClick(message: String) = clickListener(message)
}

class CustomGridLayoutManager(context: Context, spanCount: Int): GridLayoutManager(context, spanCount) {
    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}