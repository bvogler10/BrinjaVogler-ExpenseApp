package com.example.expensetracker

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.databinding.FragmentExpenseListBinding
import kotlinx.coroutines.launch


class ExpenseListFragment: Fragment() {
    private var _binding: FragmentExpenseListBinding? = null
    private val expenseRepository = ExpenseRepository.get()


    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        binding.ExpenseRecyclerView.layoutManager = LinearLayoutManager(context)


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
                lifecycleScope.launch {
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
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}