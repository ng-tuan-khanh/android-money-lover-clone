package com.narzarech.android.moneyloverclone.addtransaction.enterdate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.narzarech.android.moneyloverclone.databinding.CategoryCellBinding

class CategoryAdapter(private val clickListener: CategoryCellListener) :
    ListAdapter<String, CategoryAdapter.CategoryViewHolder>(DiffCallback) {
    class CategoryViewHolder private constructor(val binding: CategoryCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryCellBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }

        fun bind(item: String, clickListener: CategoryCellListener) {
            binding.clickListener = clickListener
            binding.categoryCell.text = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val newItem = getItem(position)
        holder.bind(newItem, clickListener)
    }
}

class CategoryCellListener(val clickListener: (String) -> Unit) {
    fun onClick(message: String) = clickListener(message)
}