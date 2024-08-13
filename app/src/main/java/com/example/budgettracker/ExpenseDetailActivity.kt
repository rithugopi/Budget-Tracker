// ExpenseDetailActivity.kt
package com.example.budgettracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_detail)

        val amount = intent.getStringExtra("EXTRA_AMOUNT")
        val category = intent.getStringExtra("EXTRA_CATEGORY")
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val date = intent.getStringExtra("EXTRA_DATE")

        val textViewDetails = findViewById<TextView>(R.id.textViewDetails)
        textViewDetails.text = "Amount: $amount\nCategory: $category\nDescription: $description\nDate: $date"
    }
}
