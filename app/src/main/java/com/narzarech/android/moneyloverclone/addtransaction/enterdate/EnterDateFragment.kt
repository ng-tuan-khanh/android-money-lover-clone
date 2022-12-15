package com.narzarech.android.moneyloverclone.addtransaction.enterdate

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
import com.narzarech.android.moneyloverclone.MainActivity
import com.narzarech.android.moneyloverclone.R
import com.narzarech.android.moneyloverclone.addtransaction.TransactionViewModel
import com.narzarech.android.moneyloverclone.database.TransactionDatabase
import com.narzarech.android.moneyloverclone.databinding.FragmentEnterDateBinding

class EnterDateFragment : Fragment() {
    private lateinit var binding: FragmentEnterDateBinding
    private lateinit var enterDateViewModel: EnterDateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_date, container, false)

        setHasOptionsMenu(true)

        // Get the necessary arguments for TransactionAmountViewModel.Factory
        val application = requireNotNull(this.activity).application
        val database = TransactionDatabase.getInstance(application).transactionDao

        // Set up the view models
        val enterDateViewModelFactory = EnterDateViewModel.Factory()
        enterDateViewModel =
            ViewModelProvider(
                this,
                enterDateViewModelFactory
            ).get(EnterDateViewModel::class.java)

        val transactionViewModelFactory = TransactionViewModel.Factory(database)
        val transactionViewModel =
            ViewModelProvider(requireActivity(), transactionViewModelFactory).get(
                TransactionViewModel::class.java
            )

        binding.lifecycleOwner = this
        binding.enterDateViewModel = enterDateViewModel

        // Set up the calendar list recycler view
        val manager = CustomGridLayoutManager(requireContext(), 7)
        val adapter = CalendarAdapter(CalendarCellListener { dayText ->
            if (dayText != "") {
                enterDateViewModel.updateSelectedDate(dayText)
                Toast.makeText(context, enterDateViewModel.selectedDate, Toast.LENGTH_SHORT)
                    .show()
            }
        })

        binding.calendarList.layoutManager = manager
        binding.calendarList.adapter = adapter

        enterDateViewModel.daysOfMonth.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        enterDateViewModel.navigateToAddTransaction.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    transactionViewModel.onNavigatedToAddTransaction(enterDateViewModel.selectedDate)
                    this.findNavController()
                        .navigate(EnterDateFragmentDirections.actionEnterDateFragmentToAddTransactionFragment())
                    enterDateViewModel.onNavigatedToAddTransaction()
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
                enterDateViewModel.onSaveButtonClicked()
                enterDateViewModel.onNavigatedToAddTransaction()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}