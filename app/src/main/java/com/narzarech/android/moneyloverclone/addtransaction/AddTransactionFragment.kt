package com.narzarech.android.moneyloverclone.addtransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.narzarech.android.moneyloverclone.MainActivity
import com.narzarech.android.moneyloverclone.R
import com.narzarech.android.moneyloverclone.database.TransactionDatabase
import com.narzarech.android.moneyloverclone.databinding.FragmentAddTransactionBinding

class AddTransactionFragment : Fragment() {
    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var addTransactionViewModel: AddTransactionViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Add a callback to onBackPressedDispatcher for this fragment
        val activity = this.requireActivity()

        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigateUp()

                transactionViewModel.onBackButtonClicked()
            }
        }

        activity.onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAddTransactionBinding>(
            inflater, R.layout.fragment_add_transaction, container, false
        )

        setHasOptionsMenu(true)

        // Get the necessary arguments for TransactionAmountViewModel.Factory
        val application = requireNotNull(this.activity).application
        val database = TransactionDatabase.getInstance(application).transactionDao

        // Set up the view models
        val addTransactionViewModelFactory = AddTransactionViewModel.Factory()
        addTransactionViewModel =
            ViewModelProvider(
                this,
                addTransactionViewModelFactory
            ).get(AddTransactionViewModel::class.java)

        val transactionViewModelFactory = TransactionViewModel.Factory(database)
        transactionViewModel =
            ViewModelProvider(requireActivity(), transactionViewModelFactory).get(
                TransactionViewModel::class.java
            )

        // Set up data binding
        binding.lifecycleOwner = this
        binding.addTransactionViewModel = addTransactionViewModel

        // Navigation
        addTransactionViewModel.navigateToEnterAmount.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    transactionViewModel.onNavigatedToEnterInfo()
                    this.findNavController()
                        .navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToEnterAmountFragment())
                    addTransactionViewModel.onNavigatedToEnterAmount()
                }
            })

        addTransactionViewModel.navigateToSelectCategory.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    this.findNavController()
                        .navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToEnterCategoryFragment())
                    addTransactionViewModel.onNavigatedToEnterCategory()
                }
            })

        addTransactionViewModel.navigateToEnterNote.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    this.findNavController()
                        .navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToEnterNoteFragment())
                    addTransactionViewModel.onNavigatedToEnterNote()
                }
            })

        addTransactionViewModel.navigateToEnterDate.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    transactionViewModel.onNavigatedToEnterInfo()
                    this.findNavController()
                        .navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToEnterDateFragment())
                    addTransactionViewModel.onNavigatedToEnterDate()
                }
            })

        addTransactionViewModel.navigateToHome.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    this.findNavController()
                        .navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToHomeFragment())
                    addTransactionViewModel.onNavigatedToHome()
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
                addTransactionViewModel.onSaveButtonClicked()
                addTransactionViewModel.onNavigatedToHome()
                transactionViewModel.onSaveButtonClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}