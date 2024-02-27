package com.example.expensetracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Expense (
    @PrimaryKey val id: UUID,
    val date: Date,
    val amount: Double,
    val type: String

)