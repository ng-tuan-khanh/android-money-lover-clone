package com.narzarech.android.moneyloverclone.addtransaction.selectcategory

import androidx.lifecycle.*
import com.narzarech.android.moneyloverclone.database.Category
import com.narzarech.android.moneyloverclone.database.CategoryDao
import com.narzarech.android.moneyloverclone.database.CategoryInfo
import com.narzarech.android.moneyloverclone.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class SelectCategoryViewModel(val categoryDao: CategoryDao) : ViewModel() {

    private var _listCategories = MutableLiveData<List<Category?>>()
    val listCategories: LiveData<List<Category?>>
        get() = _listCategories

    // Properties to handle navigation
    private val _navigateToAddTransaction = MutableLiveData<Boolean>()
    val navigateToAddTransaction: LiveData<Boolean>
        get() = _navigateToAddTransaction

    private val _navigateToAddCategory = MutableLiveData<Boolean>()
    val navigateToAddCategory: LiveData<Boolean>
        get() = _navigateToAddCategory

    init {
        _listCategories = FirebaseDatabase.readAllCategories()
    }

    // Methods to handle navigation
    fun onSaveButtonClicked() {
        _navigateToAddTransaction.value = true
    }

    fun onNavigatedToAddTransaction() {
        _navigateToAddTransaction.value = false
    }

    fun onAddCategoryButtonClicked() {
        _navigateToAddCategory.value = true
    }

    fun onNavigatedToAddCategory() {
        _navigateToAddCategory.value = false
    }


    // SelectCategoryViewModelFactory
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