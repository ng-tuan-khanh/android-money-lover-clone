package com.narzarech.android.moneyloverclone.addtransaction.enterdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class EnterDateViewModel() : ViewModel() {
    // Utility properties to create the calendar
    private val _monthText = MutableLiveData<String>()
    val monthText : LiveData<String>
        get() = _monthText

    private val _daysOfMonth = MutableLiveData<List<String>>()
    val daysOfMonth : LiveData<List<String>>
        get() = _daysOfMonth

    private var _referenceDate: LocalDate

    // Property holding the date of newly added transaction, default value is the current date
    var selectedDate: String

    // Properties to handle navigation
    private val _navigateToAddTransaction = MutableLiveData<Boolean>()
    val navigateToAddTransaction: LiveData<Boolean>
        get() = _navigateToAddTransaction

    init {
        _referenceDate = LocalDate.now()
        selectedDate = _referenceDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))

        // Set values to create the calendar
        setMonthText()
        _daysOfMonth.value = getDaysOfMonth(_referenceDate)
    }

    // Utility functions to create the calendar
    private fun getDaysOfMonth(date: LocalDate): MutableList<String> {
        val daysOfMonth = mutableListOf<String>()
        val lengthOfMonth: Int = YearMonth.from(date).lengthOfMonth()
        val dayOfWeekOfFirstDate: Int = date.withDayOfMonth(1).dayOfWeek.value

        if (dayOfWeekOfFirstDate != 7) {
            for (i in 0..41) {
                if (i < dayOfWeekOfFirstDate || i >= dayOfWeekOfFirstDate + lengthOfMonth) {
                    daysOfMonth.add("")
                }
                else {
                    daysOfMonth.add((i - dayOfWeekOfFirstDate + 1).toString())
                }
            }
        }
        else {
            for (i in 0..41) {
                if (i >= lengthOfMonth) {
                    daysOfMonth.add("")
                }
                else {
                    daysOfMonth.add((i + 1).toString())
                }
            }
        }

        return daysOfMonth
    }

    private fun setMonthText() {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        _monthText.value = _referenceDate.format(formatter)
    }

    fun onSelectPreviousMonth() {
        _referenceDate = _referenceDate.minusMonths(1)
        _daysOfMonth.value = getDaysOfMonth(_referenceDate)
        setMonthText()
    }

    fun onSelectNextMonth() {
        _referenceDate = _referenceDate.plusMonths(1)
        _daysOfMonth.value = getDaysOfMonth(_referenceDate)
        setMonthText()
    }

    // Update selected date when user click a new date
    fun updateSelectedDate(dayText: String) {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        selectedDate = dayText + " " + _referenceDate.format(formatter)
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
            if (modelClass.isAssignableFrom(EnterDateViewModel::class.java)) {
                return EnterDateViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}