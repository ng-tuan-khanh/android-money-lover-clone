package com.narzarech.android.moneyloverclone.addtransaction.selectcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.narzarech.android.moneyloverclone.database.CategoryDao
import java.time.format.DateTimeFormatter

class SelectCategoryViewModel(val categoryDao: CategoryDao) : ViewModel() {

    // Property holding the date of newly added transaction, default value is the current date
    // var selectedDate: String

    // Domain object
    private val _listCategories = MutableLiveData<List<String>>()
    val listCategories: LiveData<List<String>>
        get() = _listCategories

    // Database object
    private var _listCategoriesDB = categoryDao.getListCategories()

    // Properties to handle navigation
    private val _navigateToAddTransaction = MutableLiveData<Boolean>()
    val navigateToAddTransaction: LiveData<Boolean>
        get() = _navigateToAddTransaction

    init {
        _listCategories.value = _listCategoriesDB.value?.map { it -> it.category }
        //_listCategories.value = listOf("TEST 1", "TEST 2")
    }

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
    class Factory(val categoryDao: CategoryDao) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectCategoryViewModel::class.java)) {
                return SelectCategoryViewModel(categoryDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}