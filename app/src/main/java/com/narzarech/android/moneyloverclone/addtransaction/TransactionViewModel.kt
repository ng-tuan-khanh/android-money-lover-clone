package com.narzarech.android.moneyloverclone.addtransaction

import androidx.lifecycle.*
import com.narzarech.android.moneyloverclone.database.CategoryInfo
import com.narzarech.android.moneyloverclone.database.TransactionDao
import com.narzarech.android.moneyloverclone.database.TransactionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(val transactionDao: TransactionDao) : ViewModel() {

    private val _currentAmount = MutableLiveData<Double>()
    private val _currentCategory = MutableLiveData<String>()
    private val _currentDate = MutableLiveData<String>()

    private fun insertNewTransactionToDatabase() {
        val newTransaction = TransactionInfo(
            amount = _currentAmount.value!!,
            category = _currentCategory.value!!,
            date = _currentDate.value!!
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                transactionDao.insert(newTransaction)
            }
        }
    }

    fun onAmountSubmitted(amount: Double) {
        _currentAmount.value = amount
    }

    fun onCategorySubmitted(category: String) {
        _currentCategory.value = category
    }

    fun onDateSubmitted(date: String) {
        _currentDate.value = date
    }

    fun onSaveButtonClicked() {
        if (_currentAmount.value != null && _currentCategory.value != null && _currentDate.value != null) {
            insertNewTransactionToDatabase()
        }
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