package com.narzarech.android.moneyloverclone.addtransaction.selectcategory.addcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.narzarech.android.moneyloverclone.R
import com.narzarech.android.moneyloverclone.databinding.FragmentAddCategoryBinding

class AddCategoryFragment : Fragment() {
    private lateinit var binding: FragmentAddCategoryBinding
    private lateinit var addCategoryViewModel: AddCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_category, container, false)

        addCategoryViewModel = ViewModelProvider(this).get(AddCategoryViewModel::class.java)

        addCategoryViewModel.navigateToSelectCategory.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    this.findNavController()
                        .navigate(AddCategoryFragmentDirections.actionAddCategoryFragmentToSelectCategoryFragment())
                    addCategoryViewModel.onNavigatedToSelectCategory()
                }
            })

        return binding.root
    }

}