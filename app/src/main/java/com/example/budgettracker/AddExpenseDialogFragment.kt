package com.example.budgettracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
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

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val category = categorySpinner.selectedItem.toString()
                val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
                val description = descriptionEditText.text.toString()
                val date = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"

                val expense = Expense(category, amount, description, date)
                listener?.invoke(expense)
            }
            .setNegativeButton("Cancel", null)
            .setNeutralButton("Add Expense") { _, _ ->
                // Handle Add Expense button click if necessary
            }

        return dialogBuilder.create()
    }
}
