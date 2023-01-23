package com.narzarech.android.moneyloverclone.addtransaction.enteramount

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
import com.narzarech.android.moneyloverclone.addtransaction.TransactionViewModel
import com.narzarech.android.moneyloverclone.database.TransactionDatabase
import com.narzarech.android.moneyloverclone.databinding.FragmentEnterAmountBinding

class EnterAmountFragment : Fragment() {
    private lateinit var binding: FragmentEnterAmountBinding
    private lateinit var enterAmountViewModel: EnterAmountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEnterAmountBinding>(
            inflater,
            R.layout.fragment_enter_amount,
            container,
            false
        )

        setHasOptionsMenu(true)

        // Get the necessary arguments for TransactionAmountViewModel.Factory
        val application = requireNotNull(this.activity).application
        val database = TransactionDatabase.getInstance(application).transactionDao

        // Set up the view models
        val enterAmountViewModelFactory = EnterAmountViewModel.Factory()
        enterAmountViewModel =
            ViewModelProvider(
                this,
                enterAmountViewModelFactory
            ).get(EnterAmountViewModel::class.java)

        val transactionViewModelFactory = TransactionViewModel.Factory(database)
        val transactionViewModel =
            ViewModelProvider(requireActivity(), transactionViewModelFactory).get(
                TransactionViewModel::class.java
            )

        // Set up data binding
        binding.lifecycleOwner = this
        binding.enterAmountViewModel = enterAmountViewModel

        // Navigation
        enterAmountViewModel.navigateToAddTransaction.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    val amount = binding.amountText.text.toString().toDouble()
                    transactionViewModel.onAmountSubmitted(amount)
                    this.findNavController()
                        .navigate(EnterAmountFragmentDirections.actionEnterAmountFragmentToAddTransactionFragment())
                    enterAmountViewModel.onNavigatedToAddTransaction()
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
                enterAmountViewModel.onSaveButtonClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}