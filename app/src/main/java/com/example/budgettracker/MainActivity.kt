package com.example.budgettracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val ADD_EXPENSE_REQUEST_CODE = 1
    private var budgetLimit: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextBudget = findViewById<EditText>(R.id.editTextBudget)
        val buttonNext = findViewById<Button>(R.id.buttonNext)

        buttonNext.setOnClickListener {
            val budget = editTextBudget.text.toString().toDoubleOrNull() ?: 0.0
            budgetLimit = budget

            if (budget != null) {
                val intent = Intent(this, ExpenseListActivity::class.java).apply {
                    putExtra("EXTRA_BUDGET", budget)
                }
                startActivity(intent)
            } else {
                editTextBudget.error = "Please enter a valid budget amount"
            }

            val intent = Intent(this, ExpenseListActivity::class.java).apply {
                putExtra("TODAYS_BUDGET", budgetLimit)
            }
            startActivityForResult(intent, ADD_EXPENSE_REQUEST_CODE)
        }
    }
}
