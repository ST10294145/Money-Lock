package com.fake.money_lock

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.fake.money_lock.data.BudgetGoalViewModel

class ViewBudgetActivity : ComponentActivity() {

    private lateinit var viewModel: BudgetGoalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_budget)

        val tvBudgetGoal = findViewById<TextView>(R.id.tvBudgetGoal)
        val tvBudgetSpent = findViewById<TextView>(R.id.tvBudgetSpent)
        val tvBudgetRemaining = findViewById<TextView>(R.id.tvBudgetRemaining)

        // Replace with real values or pass via Intent
        val userId = 1
        val month = "2025-06"

        viewModel = ViewModelProvider(this)[BudgetGoalViewModel::class.java]

        viewModel.getGoalForUserAndMonth(userId, month).observe(this) { goal ->
            if (goal != null) {
                val budget = goal.monthlyBudget
                val spent = 0.0
                val remaining = budget - spent

                tvBudgetGoal.text = "Budget Goal: \$${"%.2f".format(budget)}"
                tvBudgetSpent.text = "Amount Spent: \$${"%.2f".format(spent)}"
                tvBudgetRemaining.text = "Remaining Budget: \$${"%.2f".format(remaining)}"
            } else {
                tvBudgetGoal.text = "No budget set for this month."
                tvBudgetSpent.text = ""
                tvBudgetRemaining.text = ""
            }
        }
    }
}
