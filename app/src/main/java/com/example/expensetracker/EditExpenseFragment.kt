package com.example.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.expensetracker.databinding.FragmentEditExpenseBinding
import com.example.expensetracker.databinding.FragmentNewExpenseBinding

class EditExpenseFragment: Fragment() {
    private var _binding: FragmentEditExpenseBinding? = null

    val expenseRepository = ExpenseRepository.get()
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditExpenseBinding.inflate(inflater, container, false)
        val backButton = binding.root.findViewById<Button>(R.id.backButton)
        val newTitle = binding.root.findViewById<EditText>(R.id.editTitle)
        val newExpenseType = binding.editTypeSelector
        val newAmount = binding.root.findViewById<EditText>(R.id.editExpenseAmount)
        val updateExpense = binding.root.findViewById<Button>(R.id.updateExpense)

        val categoryList = listOf(
            "All Expenses",
            "Food",
            "Entertainment",
            "Housing",
            "Utilities",
            "Fuel",
            "Automotive",
            "Misc"
        )
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categoryList
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        newExpenseType.adapter = spinnerAdapter

        return binding.root
    }
}