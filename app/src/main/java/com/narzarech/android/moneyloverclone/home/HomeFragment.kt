package com.narzarech.android.moneyloverclone.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.narzarech.android.moneyloverclone.R
import com.narzarech.android.moneyloverclone.database.TransactionDatabase
import com.narzarech.android.moneyloverclone.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false
        )

        // Set up the view model
        val application = requireNotNull(this.activity).application
        val database = TransactionDatabase.getInstance(application).transactionDao
        val homeViewModelFactory = HomeViewModel.Factory(database)
        val homeViewModel =
            ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        // Data binding
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        val adapter = TransactionAdapter()
        binding.transactionList.adapter = adapter

        homeViewModel.transactions.observe(viewLifecycleOwner, Observer {
            // TODO : Create a function to update data in ViewModel
            it?.let {
                CoroutineScope(Dispatchers.Default).launch {
                    withContext(Dispatchers.Main) {
                        adapter.submitList(it)
                    }
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume();

        val activity = this.requireActivity() as HomeActivity
        activity.hideActionBar()
    }
}