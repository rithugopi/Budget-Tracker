package com.example.budgettracker

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExpenseListActivity : AppCompatActivity() {

    private var budgetLimit: Double = 0.0
    private var totalAmountSpent: Double = 0.0
    private lateinit var textViewTotalSpent: TextView
    private val expenses = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter

    private val notificationPermissionRequestCode = 1
    private val channelId = "EXPENSE_CHANNEL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        textViewTotalSpent = findViewById(R.id.textViewTotalSpent)

        // Retrieve today's budget from the intent
        budgetLimit = intent.getDoubleExtra("TODAYS_BUDGET", 0.0)
        val textViewBudget = findViewById<TextView>(R.id.textViewBudget)
        textViewBudget.text = "Monthly Budget: $budgetLimit"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter(expenses)
        recyclerView.adapter = adapter

        // Initialize the total amount spent
        totalAmountSpent = 0.0 // This should be 0 initially, or loaded from saved state

        // Update UI with total amount spent
        updateTotalSpent()

        val buttonAddExpense = findViewById<Button>(R.id.buttonAddExpense)
        buttonAddExpense.setOnClickListener {
            val dialog = AddExpenseDialogFragment()
            dialog.setExpenseAddedListener { expense ->
                totalAmountSpent += expense.amount
                updateTotalSpent()
                expenses.add(expense)
                adapter.notifyDataSetChanged()
            }
            dialog.show(supportFragmentManager, "AddExpenseDialog")
        }
    }

    private fun updateTotalSpent() {
        textViewTotalSpent.text = "Total Amount Spent: $${"%.2f".format(totalAmountSpent)}"

        if (totalAmountSpent > budgetLimit) {
            Log.d("ExpenseListActivity", "Budget limit exceeded: $totalAmountSpent > $budgetLimit")
            checkAndSendNotification()
        }
    }

    private fun checkAndSendNotification() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                notificationPermissionRequestCode
            )
        } else {
            // Send notification if permission is granted
            sendNotification()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == notificationPermissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                sendNotification()
            } else {
                // Permission denied
                Log.d("ExpenseListActivity", "Notification permission denied")
            }
        }
    }

    private fun sendNotification() {
        val notificationManager = NotificationManagerCompat.from(this)

        // Create notification channel for Android 8.0 and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Expense Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for expense notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24) // Add a notification icon
            .setContentTitle("Expense Limit Reached!")
            .setContentText("You have exceeded today's budget limit.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 500, 1000))

        // Log notification details for debugging
        Log.d("ExpenseListActivity", "Sending notification: ${notificationBuilder.build()}")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }
}
