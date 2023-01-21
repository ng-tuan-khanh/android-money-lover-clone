package com.narzarech.android.moneyloverclone.addtransaction.selectcategory.addcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddCategoryViewModel : ViewModel() {
    private val _navigateToSelectCategory = MutableLiveData<Boolean>()
    val navigateToSelectCategory: LiveData<Boolean>
        get() = _navigateToSelectCategory

    fun onSubmitButtonClicked() {
        _navigateToSelectCategory.value = true
    }

    fun onNavigatedToSelectCategory() {
        _navigateToSelectCategory.value = false
    }
}