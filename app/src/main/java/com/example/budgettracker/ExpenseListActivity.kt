package com.example.budgettracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpenseListActivity : AppCompatActivity() {

    private var budgetLimit: Double = 0.0
    private val expenses = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        budgetLimit = intent.getDoubleExtra("EXTRA_BUDGET", 0.0)
        val textViewBudget = findViewById<TextView>(R.id.textViewBudget)
        textViewBudget.text = "Today's Budget: $budgetLimit"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fabAddExpense)
        fab.setOnClickListener {
            val dialog = AddExpenseDialogFragment()
            dialog.setExpenseAddedListener { expense ->
                expenses.add(expense)
                adapter.notifyDataSetChanged()
                checkBudget()
            }
            dialog.show(supportFragmentManager, "AddExpenseDialogFragment")
        }
    }

    private fun checkBudget() {
        val totalAmount = expenses.sumOf { it.amount }
        if (totalAmount > budgetLimit) {
            sendNotification(totalAmount)
        }
    }

    private fun sendNotification(totalAmount: Double) {
        // Notification logic here
    }
}
