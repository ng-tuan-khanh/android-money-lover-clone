package com.narzarech.android.moneyloverclone.addtransaction.selectcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class SelectCategoryViewModel() : ViewModel() {

    // Property holding the date of newly added transaction, default value is the current date
    // var selectedDate: String

    // Properties to handle navigation
    private val _navigateToAddTransaction = MutableLiveData<Boolean>()
    val navigateToAddTransaction: LiveData<Boolean>
        get() = _navigateToAddTransaction


    // Update selected date when user click a new date
    fun updateSelectedDate(dayText: String) {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        // selectedDate = dayText + " " + _referenceDate.format(formatter)
    }

    // Methods to handle navigation
    fun onSaveButtonClicked() {
        _navigateToAddTransaction.value = true
    }

    fun onNavigatedToAddTransaction() {
        _navigateToAddTransaction.value = false
    }


    // EnterDateViewModelFactory
    class Factory() : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectCategoryViewModel::class.java)) {
                return SelectCategoryViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}