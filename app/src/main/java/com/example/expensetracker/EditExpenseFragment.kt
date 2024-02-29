package com.example.expensetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.expensetracker.database.Expense
import com.example.expensetracker.databinding.FragmentEditExpenseBinding
import com.example.expensetracker.databinding.FragmentNewExpenseBinding
import java.util.Calendar
import java.util.Date
import java.util.UUID

class EditExpenseFragment: Fragment() {
    private var _binding: FragmentEditExpenseBinding? = null
    private val calendar = Calendar.getInstance()

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

        backButton.setOnClickListener {
            val listExpenseFragment = ExpenseListFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, listExpenseFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val expenseId = arguments?.getString("id")
        var expenseTitle = arguments?.getString("title")
        var expenseAmount = arguments?.getDouble("amount")
        val expenseDate = arguments?.getString("date")
        var expenseType = arguments?.getString("type")

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
        binding.editTypeSelector.adapter = spinnerAdapter

        val categoryIndex = categoryList.indexOf(expenseType)
        if (categoryIndex != -1) {
            binding.editTypeSelector.setSelection(categoryIndex)
        }

        view.findViewById<EditText>(R.id.editTitle).hint = expenseTitle
        view.findViewById<EditText>(R.id.editExpenseAmount).hint = expenseAmount.toString()

        binding.editTitle.setOnClickListener {
            expenseTitle = binding.editTitle.text.toString()
        }
        binding.editTypeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                expenseType = binding.editTypeSelector.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.editExpenseAmount.setOnClickListener {
            expenseAmount = binding.editExpenseAmount.text.toString().toDouble()
        }
        binding.updateExpense.setOnClickListener {
            val expenseId = arguments?.getString("id")
            val uuid: UUID = UUID.fromString(expenseId)
        }
    }
}