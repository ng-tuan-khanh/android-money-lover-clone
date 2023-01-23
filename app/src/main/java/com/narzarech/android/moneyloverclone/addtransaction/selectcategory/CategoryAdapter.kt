package com.narzarech.android.moneyloverclone.addtransaction.selectcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.narzarech.android.moneyloverclone.database.CategoryInfo
import com.narzarech.android.moneyloverclone.databinding.CategoryCellBinding

class CategoryAdapter(private val clickListener: CategoryCellListener) :
    ListAdapter<CategoryInfo, CategoryAdapter.CategoryViewHolder>(DiffCallback) {
    class CategoryViewHolder private constructor(val binding: CategoryCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryCellBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }

        fun bind(item: CategoryInfo, clickListener: CategoryCellListener) {
            binding.category = item
            binding.clickListener = clickListener
            binding.categoryCell.text = item.category
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CategoryInfo>() {
        override fun areItemsTheSame(oldItem: CategoryInfo, newItem: CategoryInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryInfo, newItem: CategoryInfo): Boolean {
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

class CategoryCellListener(val clickListener: (CategoryInfo) -> Unit) {
    fun onClick(categoryInfo: CategoryInfo) = clickListener(categoryInfo)
}