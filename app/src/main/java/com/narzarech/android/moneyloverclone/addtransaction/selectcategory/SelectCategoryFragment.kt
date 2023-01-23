package com.narzarech.android.moneyloverclone.addtransaction.selectcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.narzarech.android.moneyloverclone.MainActivity
import com.narzarech.android.moneyloverclone.R
import com.narzarech.android.moneyloverclone.addtransaction.TransactionViewModel
import com.narzarech.android.moneyloverclone.database.CategoryDatabase
import com.narzarech.android.moneyloverclone.database.TransactionDatabase
import com.narzarech.android.moneyloverclone.databinding.FragmentSelectCategoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectCategoryFragment : Fragment() {
    private lateinit var binding: FragmentSelectCategoryBinding
    private lateinit var selectCategoryViewModel: SelectCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_category, container, false)

        setHasOptionsMenu(true)

        // Get a DAO for TransactionDatabase and CategoryDatabase
        val application = requireNotNull(this.activity).application
        val transactionDao = TransactionDatabase.getInstance(application).transactionDao
        val categoryDao = CategoryDatabase.getInstance(application).categoryDao

        // Set up the view models
        val selectCategoryViewModelFactory = SelectCategoryViewModel.Factory(categoryDao)
        selectCategoryViewModel =
            ViewModelProvider(
                this,
                selectCategoryViewModelFactory
            ).get(SelectCategoryViewModel::class.java)

        val transactionViewModelFactory = TransactionViewModel.Factory(transactionDao)
        val transactionViewModel =
            ViewModelProvider(requireActivity(), transactionViewModelFactory).get(
                TransactionViewModel::class.java
            )

        binding.lifecycleOwner = this
        binding.selectCategoryViewModel = selectCategoryViewModel

        // Set up the category list recycler view
        val manager = LinearLayoutManager(requireContext())
        val adapter = CategoryAdapter(CategoryCellListener { category ->
            // Click listener for item in the recycler view

            // Pass the category info for the current transaction
            transactionViewModel.onCategorySubmitted(category.category)
            Toast.makeText(context, category.category, Toast.LENGTH_SHORT).show()
        })

        binding.categoryList.layoutManager = manager
        binding.categoryList.adapter = adapter

        selectCategoryViewModel.listCategories.observe(viewLifecycleOwner, Observer {
            it?.let {
                CoroutineScope(Dispatchers.Default).launch {
                    withContext(Dispatchers.Main) {
                        adapter.submitList(it)
                    }
                }
            }
        })

        // Navigation observers
        selectCategoryViewModel.navigateToAddTransaction.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
//                    transactionViewModel.onNavigatedToAddTransaction(enterDateViewModel.selectedDate)
                    this.findNavController()
                        .navigate(SelectCategoryFragmentDirections.actionSelectCategoryFragmentToAddTransactionFragment())
                    selectCategoryViewModel.onNavigatedToAddTransaction()
                }
            })

        selectCategoryViewModel.navigateToAddCategory.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    this.findNavController()
                        .navigate(SelectCategoryFragmentDirections.actionSelectCategoryFragmentToAddCategoryFragment())
                    selectCategoryViewModel.onNavigatedToAddCategory()
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
        val activity = this.requireActivity() as MainActivity
        return when (item.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
                true
            }
            R.id.action_save -> {
                selectCategoryViewModel.onSaveButtonClicked()
                selectCategoryViewModel.onNavigatedToAddTransaction()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}