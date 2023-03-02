package com.narzarech.android.moneyloverclone.addtransaction

import androidx.lifecycle.*
import com.narzarech.android.moneyloverclone.database.FirebaseDatabase
import com.narzarech.android.moneyloverclone.database.TransactionDao
import com.narzarech.android.moneyloverclone.database.TransactionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(val transactionDao: TransactionDao) : ViewModel() {

    private val _currentAmount = MutableLiveData<Double>()
    private val _currentCategory = MutableLiveData<String>()
    private val _currentNote = MutableLiveData<String>()
    private val _currentDate = MutableLiveData<String>()

    private fun insertNewTransactionToRoomDatabase() {
        val newTransaction = TransactionInfo(
            amount = _currentAmount.value!!,
            category = _currentCategory.value!!,
            note = _currentNote.value!!,
            date = _currentDate.value!!
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                transactionDao.insert(newTransaction)
            }
        }
    }

    private fun insertNewTransactionToFirebaseDatabase() {
        val amount = _currentAmount.value!!
        val category = _currentCategory.value!!
        val note = _currentNote.value!!
        val date = _currentDate.value!!

        FirebaseDatabase.writeTransaction(amount, note, date)
    }

    fun onAmountSubmitted(amount: Double) {
        _currentAmount.value = amount
    }

    fun onCategorySubmitted(category: String) {
        _currentCategory.value = category
    }

    fun onNoteSubmitted(note: String) {
        _currentNote.value = note
    }

    fun onDateSubmitted(date: String) {
        _currentDate.value = date
    }

    fun onSaveButtonClicked() {
        if (_currentAmount.value != null && _currentCategory.value != null && _currentNote.value != null && _currentDate.value != null) {
            insertNewTransactionToFirebaseDatabase()
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