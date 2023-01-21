package com.narzarech.android.moneyloverclone.addtransaction.selectcategory.addcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.narzarech.android.moneyloverclone.MainActivity
import com.narzarech.android.moneyloverclone.R
import com.narzarech.android.moneyloverclone.database.CategoryDatabase
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

        setHasOptionsMenu(true)

        // Get a DAO for TransactionDatabase and CategoryDatabase
        val application = requireNotNull(this.activity).application
        val categoryDao = CategoryDatabase.getInstance(application).categoryDao

        val addCategoryViewModelFactory = AddCategoryViewModel.Factory(categoryDao)
        addCategoryViewModel = ViewModelProvider(
            this,
            addCategoryViewModelFactory
        ).get(AddCategoryViewModel::class.java)

        binding.lifecycleOwner = this
        binding.addCategoryViewModel = addCategoryViewModel

        addCategoryViewModel.navigateToSelectCategory.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    val categoryText = binding.categoryText.text.toString()
                    this.findNavController()
                        .navigate(AddCategoryFragmentDirections.actionAddCategoryFragmentToSelectCategoryFragment())
                    addCategoryViewModel.onNavigatedToSelectCategory(categoryText)
                }
            })

        return binding.root
    }

    override fun onResume() {
        super.onResume();

        val activity = this.requireActivity() as MainActivity
        activity.showActionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activity = this.requireActivity()

        return when (item.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
                true
            }
            R.id.action_save -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}