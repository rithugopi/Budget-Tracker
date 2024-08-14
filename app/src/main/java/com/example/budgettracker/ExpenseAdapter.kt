package com.example.budgettracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val expenseList: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCategory: TextView = itemView.findViewById(R.id.textViewCategory)
        val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.textViewCategory.text = expense.category
        holder.textViewAmount.text = "Amount: $${"%.2f".format(expense.amount)}"
        holder.textViewDescription.text = expense.description
        holder.textViewDate.text = "Date: ${expense.date}"
    }

    override fun getItemCount() = expenseList.size
}
