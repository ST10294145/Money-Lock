package com.fake.money_lock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeScreen : ComponentActivity() {

    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen) // Use this for XML-based UI

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
            val intent = Intent(this, ViewExpenseActivity::class.java)
            startActivity(intent)
        }

        // Budget Goals
        val budgetGoalsButton = findViewById<Button>(R.id.btnBudgetGoals)
        budgetGoalsButton.setOnClickListener {
            val intent = Intent(this, BudgetGoals::class.java)
            startActivity(intent)
        }

        // Add the Account Settings button
        val accountSettingsButton = findViewById<Button>(R.id.btnAccountSettings)
        accountSettingsButton.setOnClickListener {
            val intent = Intent(this, AccountSettings::class.java)
            startActivity(intent)
        }

        // View Budget button (corrected line below)
        val viewBudgetButton = findViewById<Button>(R.id.btnViewBudget)
        viewBudgetButton.setOnClickListener {
            val intent = Intent(this, ViewBudget::class.java) // <-- FIXED
            startActivity(intent)
        }
    }
}
