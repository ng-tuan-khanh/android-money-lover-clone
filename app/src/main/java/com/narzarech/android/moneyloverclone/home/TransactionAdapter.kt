package com.narzarech.android.moneyloverclone.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.narzarech.android.moneyloverclone.database.Transaction
import com.narzarech.android.moneyloverclone.databinding.TransactionCellBinding

class TransactionAdapter() :
    ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(DiffCallBack) {
    class TransactionViewHolder private constructor(val binding: TransactionCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): TransactionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TransactionCellBinding.inflate(layoutInflater, parent, false)
                return TransactionViewHolder(binding)
            }
        }

        fun bind(item: Transaction) {
            //binding.transactionCategory.text = item.category
            binding.transactionAmount.text = "₫" + item.amount.toString()
            binding.transactionNote.text = item.note
            binding.transactionDate.text = item.date
            binding.transaction = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Transaction,
            newItem: Transaction
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val newItem = getItem(position)
        holder.bind(newItem)
    }
}