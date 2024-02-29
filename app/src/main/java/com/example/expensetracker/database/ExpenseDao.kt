package com.example.expensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.Date
import java.util.UUID

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expense WHERE id=(:id)")
    suspend fun getExpense(id: UUID): Expense

    @Query("SELECT * FROM expense ORDER BY date DESC")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM expense WHERE type = :category ORDER BY date DESC")
    suspend fun getExpensesByCategory(category: String): List<Expense>

    @Update
    suspend fun updateExpense(expense: Expense)

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteExpense(id: UUID)

}