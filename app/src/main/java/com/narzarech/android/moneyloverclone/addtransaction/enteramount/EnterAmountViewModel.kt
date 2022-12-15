package com.narzarech.android.moneyloverclone.addtransaction.enteramount

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EnterAmountViewModel() : ViewModel() {

    // Properties to handle navigation
    private val _navigateToAddTransaction = MutableLiveData<Boolean>()
    val navigateToAddTransaction: LiveData<Boolean>
        get() = _navigateToAddTransaction

    // Methods to handle navigation
    fun onSaveButtonClicked() {
        _navigateToAddTransaction.value = true
    }

    fun onSubmitButtonClicked() {
        _navigateToAddTransaction.value = true
    }

    fun onNavigatedToAddTransaction() {
        _navigateToAddTransaction.value = false
    }

    // EnterAmountViewModelFactory
    class Factory() : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EnterAmountViewModel::class.java)) {
                return EnterAmountViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}