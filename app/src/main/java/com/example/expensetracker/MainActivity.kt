package com.example.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDao
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ExpenseRepository.initialize(applicationContext)

        val expenseRepository: ExpenseRepository = ExpenseRepository.get()
        lifecycleScope.launch {
            val expense =Expense(UUID.randomUUID(), "Test Expense", Date(), 50.0, "Food")
            expenseRepository.insertExpense(expense)

        }
    }
}