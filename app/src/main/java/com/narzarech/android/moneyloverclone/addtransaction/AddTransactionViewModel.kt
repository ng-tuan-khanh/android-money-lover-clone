package com.narzarech.android.moneyloverclone.addtransaction

import androidx.lifecycle.*
import com.narzarech.android.moneyloverclone.database.TransactionDao
import com.narzarech.android.moneyloverclone.database.TransactionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTransactionViewModel(): ViewModel() {

    // Properties to handle navigation
    private val _navigateToEnterAmount = MutableLiveData<Boolean>()
    val navigateToEnterAmount: LiveData<Boolean>
        get() = _navigateToEnterAmount

    private val _navigateToSelectCategory = MutableLiveData<Boolean>()
    val navigateToSelectCategory: LiveData<Boolean>
        get() = _navigateToSelectCategory

    private val _navigateToEnterNote = MutableLiveData<Boolean>()
    val navigateToEnterNote: LiveData<Boolean>
        get() = _navigateToEnterNote

    private val _navigateToEnterDate = MutableLiveData<Boolean>()
    val navigateToEnterDate: LiveData<Boolean>
        get() = _navigateToEnterDate

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome


    // Methods to handle navigation
    fun onEnterAmountClicked() {
        _navigateToEnterAmount.value = true
    }

    fun onNavigatedToEnterAmount() {
        _navigateToEnterAmount.value = false
    }

    fun onSelectCategoryClicked() {
        _navigateToSelectCategory.value = true
    }

    fun onNavigatedToEnterCategory() {
        _navigateToSelectCategory.value = false
    }

    fun onEnterNoteClicked() {
        _navigateToEnterNote.value = true
    }

    fun onNavigatedToEnterNote() {
        _navigateToEnterNote.value = false
    }

    fun onEnterDateClicked() {
        _navigateToEnterDate.value = true
    }

    fun onNavigatedToEnterDate() {
        _navigateToEnterDate.value = false
    }

    fun onSaveButtonClicked() {
        _navigateToHome.value = true
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }

    class Factory() : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
                return AddTransactionViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}