package com.narzarech.android.moneyloverclone.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ktx.Firebase
import com.narzarech.android.moneyloverclone.database.FirebaseDatabase
import com.narzarech.android.moneyloverclone.database.Transaction
import com.narzarech.android.moneyloverclone.database.TransactionDao
import com.narzarech.android.moneyloverclone.database.TransactionInfo

class HomeViewModel(database: TransactionDao) : ViewModel() {

    private val _transactions = FirebaseDatabase.readAllTransactions()
    val transactions: LiveData<List<Transaction?>>
        get() = _transactions

    class Factory(
        private val dataSource: TransactionDao
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}