package com.example.budgettracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_detail)

        val category = intent.getStringExtra("CATEGORY")
        val amount = intent.getDoubleExtra("AMOUNT", 0.0)
        val description = intent.getStringExtra("DESCRIPTION")
        val date = intent.getStringExtra("DATE")

        val textViewCategory: TextView = findViewById(R.id.textViewCategory)
        val textViewAmount: TextView = findViewById(R.id.textViewAmount)
        val textViewDescription: TextView = findViewById(R.id.textViewDescription)
        val textViewDate: TextView = findViewById(R.id.textViewDate)

        textViewCategory.text = "Category: $category"
        textViewAmount.text = "Amount: $${"%.2f".format(amount)}"
        textViewDescription.text = "Description: $description"
        textViewDate.text = "Date: $date"
    }
}
