package com.example.budgettracker

data class Expense(
    val category: String,
    val amount: Double,
    val description: String,
    val date: String
)