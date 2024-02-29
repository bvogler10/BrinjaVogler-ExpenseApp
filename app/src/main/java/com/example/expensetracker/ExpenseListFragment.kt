package com.example.expensetracker

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentExpenseListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ExpenseListFragment: Fragment() {
    private var _binding: FragmentExpenseListBinding? = null

    val expenseRepository = ExpenseRepository.get()
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        binding.ExpenseRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ExpenseListAdapter(emptyList())


        val categorySpinner = binding.ExpenseCategories
        val categoryList = listOf("All Expenses","Food", "Entertainment", "Housing", "Utilities", "Fuel", "Automotive", "Misc")
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, categoryList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory: String = categoryList[position]
                GlobalScope.launch {
                    if (selectedCategory == "All") {
                        expenseRepository.getAllExpenses()
                    } else {
                        expenseRepository.getExpensesByCategory(selectedCategory)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        binding.ExpenseRecyclerView.adapter = adapter
        expenseListViewModel.expenses.observe(viewLifecycleOwner) { expenseList ->
            adapter.updateExpenseList(expenseList)
        }

        return binding.root
    }
}