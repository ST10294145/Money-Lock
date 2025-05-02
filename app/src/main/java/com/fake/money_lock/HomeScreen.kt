package com.fake.money_lock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeScreen : AppCompatActivity() {

    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set welcome text
        nameTextView = findViewById(R.id.tvWelcome)
        val name = intent.getStringExtra("name") ?: "User"
        nameTextView.text = "Welcome, $name"

        // Add Expense button
        val addExpenseButton = findViewById<Button>(R.id.btnAddExpense)
        addExpenseButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            intent.putExtra("user_id", 1) // Replace with actual user ID if dynamic
            startActivity(intent)
        }

        // View Expenses button
        val viewExpensesButton = findViewById<Button>(R.id.btnViewExpenses)
        viewExpensesButton.setOnClickListener {
            // Navigate to ViewExpenseActivity
            val intent = Intent(this, ViewExpenseActivity::class.java)
            startActivity(intent)
        }

        // Budget Goals
        val budgetGoalsButton = findViewById<Button>(R.id.btnBudgetGoals)
        budgetGoalsButton.setOnClickListener {
            val intent = Intent(this, BudgetGoals::class.java)
            startActivity(intent)
        }

        // TODO: Add the other buttons
    }
}
