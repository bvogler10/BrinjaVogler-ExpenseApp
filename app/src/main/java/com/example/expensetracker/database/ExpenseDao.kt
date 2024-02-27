package com.example.expensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.Date
import java.util.UUID

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expense WHERE id=(:id)")
    suspend fun getExpense(id: UUID): Expense

    @Query("UPDATE expense SET amount = :newAmount WHERE id = :id")
    suspend fun updateExpenseAmount(id: UUID, newAmount: Double)

    @Query("UPDATE expense SET type = :newType WHERE id = :id")
    suspend fun updateExpenseCategory(id: UUID, newType: String)

    @Query("UPDATE expense SET date = :newDate WHERE id = :id")
    suspend fun updateExpenseDate(id: UUID, newDate: Date)

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteExpense(id: UUID)

}