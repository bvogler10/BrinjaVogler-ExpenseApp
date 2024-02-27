package com.example.expensetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.database.Expense
import com.example.expensetracker.database.DateConverter

@Database(entities = [Expense::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    companion object {
        @Volatile
        private var instance: ExpenseDatabase? = null
    }
}