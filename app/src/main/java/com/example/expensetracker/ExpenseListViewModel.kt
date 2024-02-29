package com.example.expensetracker

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.database.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.coroutineContext

class ExpenseListViewModel: ViewModel()  {
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> get() = _expenses

    private val expenseRepository = ExpenseRepository.get()

    init {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                expenseRepository.getAllExpenses()
            }
            val expensesList = response
            if (expensesList != null) {
                // Update LiveData on the main thread
                _expenses.postValue(expensesList)
            }
        }
    }

    fun getExpensesByCategory(type: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                expenseRepository.getExpensesByCategory(type)
            }
            val expensesList = response
            if (expensesList != null) {
                // Update LiveData on the main thread
                _expenses.postValue(expensesList)
            }
        }
    }

    fun getAllExpenses() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                expenseRepository.getAllExpenses()
            }
            val expensesList = response
            if (expensesList != null) {
                // Update LiveData on the main thread
                _expenses.postValue(expensesList)
            }
        }
    }
}