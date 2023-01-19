package com.narzarech.android.moneyloverclone.addtransaction.selectcategory

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.narzarech.android.moneyloverclone.MainActivity
import com.narzarech.android.moneyloverclone.R
import com.narzarech.android.moneyloverclone.addtransaction.TransactionViewModel
import com.narzarech.android.moneyloverclone.addtransaction.enterdate.CategoryAdapter
import com.narzarech.android.moneyloverclone.addtransaction.enterdate.CategoryCellListener
import com.narzarech.android.moneyloverclone.database.TransactionDatabase
import com.narzarech.android.moneyloverclone.databinding.FragmentSelectCategoryBinding

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

        // Get the necessary arguments for TransactionAmountViewModel.Factory
        val application = requireNotNull(this.activity).application
        val database = TransactionDatabase.getInstance(application).transactionDao

        // Set up the view models
        val selectCategoryViewModelFactory = SelectCategoryViewModel.Factory()
        selectCategoryViewModel =
            ViewModelProvider(
                this,
                selectCategoryViewModelFactory
            ).get(SelectCategoryViewModel::class.java)

        val transactionViewModelFactory = TransactionViewModel.Factory(database)
        val transactionViewModel =
            ViewModelProvider(requireActivity(), transactionViewModelFactory).get(
                TransactionViewModel::class.java
            )

        binding.lifecycleOwner = this
        binding.selectCategoryViewModel = selectCategoryViewModel

        // Set up the category list recycler view
        val manager = LinearLayoutManager(requireContext())
        val adapter = CategoryAdapter(CategoryCellListener { categoryText ->
//            if (categoryText != "") {
//                enterDateViewModel.updateSelectedDate(dayText)
//                Toast.makeText(context, enterDateViewModel.selectedDate, Toast.LENGTH_SHORT)
//                    .show()
//            }
        })

        binding.categoryList.layoutManager = manager
        binding.categoryList.adapter = adapter

//        enterDateViewModel.daysOfMonth.observe(viewLifecycleOwner, Observer {
//            adapter.submitList(it)
//        })

        // Navigation observers
        selectCategoryViewModel.navigateToAddTransaction.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    //transactionViewModel.onNavigatedToAddTransaction(enterDateViewModel.selectedDate)
                    this.findNavController()
                        .navigate(SelectCategoryFragmentDirections.actionSelectCategoryFragmentToAddTransactionFragment())
                    selectCategoryViewModel.onNavigatedToAddTransaction()
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