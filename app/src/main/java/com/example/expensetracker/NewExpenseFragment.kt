package com.example.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.database.Expense
import com.example.expensetracker.databinding.FragmentExpenseListBinding
import com.example.expensetracker.databinding.FragmentNewExpenseBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class NewExpenseFragment: Fragment() {
    private var _binding: FragmentNewExpenseBinding? = null

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
        _binding = FragmentNewExpenseBinding.inflate(inflater, container, false)

        val backButton = binding.root.findViewById<Button>(R.id.backButton)
        val setTitle = binding.root.findViewById<EditText>(R.id.titleEntry)
        val expenseType = binding.typeSelector
        val expenseAmount = binding.root.findViewById<EditText>(R.id.expenseAmount)
        val createExpense = binding.root.findViewById<Button>(R.id.createExpense)

        val categoryList = listOf("All Expenses","Food", "Entertainment", "Housing", "Utilities", "Fuel", "Automotive", "Misc")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        expenseType.adapter = spinnerAdapter

        createExpense.setOnClickListener {
            if(setTitle.text.isNotEmpty() && expenseAmount.text.toString() != ".") {
                val newExpense = Expense(
                    id = UUID.randomUUID(),
                    title = setTitle.text.toString(),
                    date = Date(),
                    amount = expenseAmount.text.toString().toDouble(),
                    type = expenseType.selectedItem.toString()
                )

                lifecycleScope.launch {
                    expenseRepository.insertExpense(newExpense)
                }

                val listExpenseFragment = ExpenseListFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, listExpenseFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                Toast.makeText(requireContext(), "Please make sure all inputs are valid!", Toast.LENGTH_LONG).show()
            }
        }
        backButton.setOnClickListener {
            val listExpenseFragment = ExpenseListFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, listExpenseFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return binding.root
    }
}