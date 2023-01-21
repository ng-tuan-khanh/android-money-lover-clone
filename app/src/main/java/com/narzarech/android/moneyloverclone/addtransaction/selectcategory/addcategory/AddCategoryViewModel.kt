package com.narzarech.android.moneyloverclone.addtransaction.selectcategory.addcategory

import androidx.lifecycle.*
import com.narzarech.android.moneyloverclone.addtransaction.selectcategory.SelectCategoryViewModel
import com.narzarech.android.moneyloverclone.database.CategoryDao
import com.narzarech.android.moneyloverclone.database.CategoryInfo
import com.narzarech.android.moneyloverclone.database.TransactionInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCategoryViewModel(private val categoryDao: CategoryDao) : ViewModel() {
    private val _navigateToSelectCategory = MutableLiveData<Boolean>()
    val navigateToSelectCategory: LiveData<Boolean>
        get() = _navigateToSelectCategory

    fun onSubmitButtonClicked() {
        _navigateToSelectCategory.value = true
    }

    fun onNavigatedToSelectCategory(categoryText: String) {
        insertNewCategoryToDatabase(categoryText)
        _navigateToSelectCategory.value = false
    }

    private fun insertNewCategoryToDatabase(categoryText: String) {
        viewModelScope.launch {
            val newCategory = CategoryInfo(category = categoryText)
            withContext(Dispatchers.IO) {
                categoryDao.insert(newCategory)
            }
        }
    }


    // AddCategoryViewModelFactory
    class Factory(val categoryDao: CategoryDao) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddCategoryViewModel::class.java)) {
                return AddCategoryViewModel(categoryDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}