package com.narzarech.android.moneyloverclone.addtransaction

import androidx.lifecycle.*
import com.narzarech.android.moneyloverclone.database.TransactionDao
import com.narzarech.android.moneyloverclone.database.TransactionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(val transactionDao: TransactionDao) : ViewModel() {

    // Properties to hold data
    private val _currentTransaction = MutableLiveData<TransactionInfo?>()
    val currentTransaction: LiveData<TransactionInfo?>
        get() = _currentTransaction

    private var _shouldInsertNewTransaction = true
    private var _shouldRemoveLatestTransaction = false

    // Methods to interact with the database
    private suspend fun getLatestTransactionFromDatabase() : TransactionInfo? {
        return withContext(Dispatchers.IO) {
            transactionDao.getLatestTransaction()
        }
    }

    private fun insertNewTransactionToDatabase() {
        viewModelScope.launch {
            val newTransaction = TransactionInfo()
            withContext(Dispatchers.IO) {
                transactionDao.insert(newTransaction)
            }
            _currentTransaction.value = getLatestTransactionFromDatabase()
        }
    }

    private fun removeLatestTransactionFromDatabase() {
        _currentTransaction.value?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    transactionDao.remove(_currentTransaction.value!!.id)
                }
                _currentTransaction.value = getLatestTransactionFromDatabase()
            }
        }
    }

    private fun updateTransactionToDatabase(amount: Double) {
        viewModelScope.launch {
            _currentTransaction.value!!.amount = amount
            withContext(Dispatchers.IO) {
                transactionDao.update(_currentTransaction.value!!)
            }
        }
    }

    private fun updateTransactionToDatabase(date: String) {
        viewModelScope.launch {
            _currentTransaction.value!!.date = date
            withContext(Dispatchers.IO) {
                transactionDao.update(_currentTransaction.value!!)
            }
        }
    }

    // Methods to handle the navigation
    // Navigating to enter information of the new transaction
    fun onNavigatedToEnterInfo() {
        if (_shouldInsertNewTransaction) {
            insertNewTransactionToDatabase()
            _shouldInsertNewTransaction = false
        }
        _shouldRemoveLatestTransaction = true
    }

    fun onNavigatedToAddTransaction(amount: Double) {
        updateTransactionToDatabase(amount)
    }

    // Navigating back to AddTransaction after entering info
    fun onNavigatedToAddTransaction(date: String) {
        if (date != "") {
            updateTransactionToDatabase(date)
        }
    }

    // Navigating back to Home
    fun onBackButtonClicked() {
        _shouldInsertNewTransaction = true
        if (_shouldRemoveLatestTransaction) {
            removeLatestTransactionFromDatabase()
            _shouldRemoveLatestTransaction = false
        }
    }

    fun onSaveButtonClicked() {
        _shouldInsertNewTransaction = true
        _shouldRemoveLatestTransaction = false
    }

    class Factory(val transactionDao: TransactionDao) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
                return TransactionViewModel(transactionDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}