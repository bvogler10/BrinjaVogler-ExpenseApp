package com.example.expensetracker

import android.app.Application

class ExpenseTracker: Application() {
    override fun onCreate() {
        super.onCreate()
        ExpenseRepository.initialize(this)
    }
}