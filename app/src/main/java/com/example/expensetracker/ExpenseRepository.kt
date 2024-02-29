package com.example.expensetracker

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "expenses.db"

class ExpenseRepository constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database: ExpenseDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ExpenseDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    suspend fun insertExpense(expense: Expense) = database.expenseDao().insertExpense(expense)

    suspend fun getAllExpenses(): List<Expense> = database.expenseDao().getAllExpenses()

    suspend fun getExpense(id: UUID): Expense = database.expenseDao().getExpense(id)

    suspend fun getExpensesByCategory(category: String): List<Expense> = database.expenseDao().getExpensesByCategory(category)

    fun updateExpense(expense: Expense) {
        coroutineScope.launch {
            database.expenseDao().updateExpense(expense)
        }
    }

    companion object {
        private var INSTANCE: ExpenseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ExpenseRepository(context)
            }
        }

        fun get(): ExpenseRepository {
            return INSTANCE
                ?: throw IllegalStateException("ExpenseRepository must be initialized")
        }
    }
}
