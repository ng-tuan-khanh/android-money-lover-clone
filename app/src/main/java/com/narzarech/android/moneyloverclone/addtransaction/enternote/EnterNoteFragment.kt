package com.narzarech.android.moneyloverclone.addtransaction.enternote

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
import com.narzarech.android.moneyloverclone.databinding.FragmentEnterNoteBinding

class EnterNoteFragment : Fragment() {
    private lateinit var binding: FragmentEnterNoteBinding
    private lateinit var enterNoteViewModel: EnterNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_enter_note,
            container,
            false
        )

        setHasOptionsMenu(true)

        // Get the necessary arguments for TransactionAmountViewModel.Factory
        val application = requireNotNull(this.activity).application
        val database = TransactionDatabase.getInstance(application).transactionDao

        // Set up the view models
        val enterNoteViewModelFactory = EnterNoteViewModel.Factory()
        enterNoteViewModel =
            ViewModelProvider(
                this,
                enterNoteViewModelFactory
            ).get(EnterNoteViewModel::class.java)

        val transactionViewModelFactory = TransactionViewModel.Factory(database)
        val transactionViewModel =
            ViewModelProvider(requireActivity(), transactionViewModelFactory).get(
                TransactionViewModel::class.java
            )

        // Set up data binding
        binding.lifecycleOwner = this
        binding.enterNoteViewModel = enterNoteViewModel

        // Navigation
        enterNoteViewModel.navigateToAddTransaction.observe(
            viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate) {
                    val note = binding.noteText.text.toString()
                    transactionViewModel.onNoteSubmitted(note)
                    this.findNavController()
                        .navigate(EnterNoteFragmentDirections.actionEnterNoteFragmentToAddTransactionFragment())
                    enterNoteViewModel.onNavigatedToAddTransaction()
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
                enterNoteViewModel.onSaveButtonClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}