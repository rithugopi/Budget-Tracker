package com.example.budgettracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment

class AddExpenseDialogFragment : DialogFragment() {

    private var listener: ((Expense) -> Unit)? = null

    fun setExpenseAddedListener(listener: (Expense) -> Unit) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_add_expense, null)

        val categorySpinner = view.findViewById<Spinner>(R.id.spinnerActivityType)
        val amountEditText = view.findViewById<EditText>(R.id.editTextAmount)
        val descriptionEditText = view.findViewById<EditText>(R.id.editTextDescription)
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)

        // Set up the spinner with categories
        val categories = arrayOf("Home", "Clothes", "Grocery", "Entertainment", "Utilities", "Transportation", "Other")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val category = categorySpinner.selectedItem.toString()
                val amountText = amountEditText.text.toString()
                val amount = amountText.toDoubleOrNull() ?: 0.0
                if (amountText.isNotEmpty() && amount > 0) {
                    val description = descriptionEditText.text.toString()
                    val date = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"

                    val expense = Expense(category, amount, description, date)
                    listener?.invoke(expense)
                } else {
                    // Show error message if the amount is invalid
                    amountEditText.error = "Enter a valid amount"
                }
            }
            .setNegativeButton("Cancel", null)

        return dialogBuilder.create()
    }
}
