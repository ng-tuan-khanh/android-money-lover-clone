package com.narzarech.android.moneyloverclone.addtransaction.enternote

import androidx.lifecycle.*

class EnterNoteViewModel() : ViewModel() {

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

    // EnterNoteViewModelFactory
    class Factory() : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EnterNoteViewModel::class.java)) {
                return EnterNoteViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}