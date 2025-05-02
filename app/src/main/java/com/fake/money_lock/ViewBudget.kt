package com.fake.money_lock

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fake.money_lock.data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewBudget : AppCompatActivity() {

    private lateinit var tvBudgetTitle: TextView
    private lateinit var tvBudgetGoal: TextView
    private lateinit var tvBudgetSpent: TextView
    private lateinit var tvBudgetRemaining: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_budget)

        tvBudgetTitle = findViewById(R.id.tvBudgetTitle)
        tvBudgetGoal = findViewById(R.id.tvBudgetGoal)
        tvBudgetSpent = findViewById(R.id.tvBudgetSpent)
        tvBudgetRemaining = findViewById(R.id.tvBudgetRemaining)

        // Fetch budget data from the database
        loadBudgetData()
    }

    private fun loadBudgetData() {
        lifecycleScope.launch {
            try {
                // Assuming you fetch the user info from the UserDatabase.
                val user = UserDatabase.getDatabase(applicationContext).userDao().readAllData().value?.firstOrNull()

                if (user != null) {
                    val budgetGoal = user.budgetGoal // Budget goal for the user
                    val amountSpent = user.amountSpent // Amount spent by the user
                    val remainingBudget = budgetGoal - amountSpent // Calculate remaining budget

                    // Update UI on the main thread
                    withContext(Dispatchers.Main) {
                        tvBudgetGoal.text = "Budget Goal: $$budgetGoal"
                        tvBudgetSpent.text = "Amount Spent: $$amountSpent"
                        tvBudgetRemaining.text = "Remaining Budget: $$remainingBudget"
                    }
                } else {
                    // Handle the case where no user data is available
                    withContext(Dispatchers.Main) {
                        tvBudgetGoal.text = "No budget goal set"
                        tvBudgetSpent.text = "No amount spent"
                        tvBudgetRemaining.text = "No remaining budget"
                    }
                }
            } catch (e: Exception) {
                // Handle any potential errors
                withContext(Dispatchers.Main) {
                    tvBudgetGoal.text = "Error loading budget data"
                    tvBudgetSpent.text = ""
                    tvBudgetRemaining.text = ""
                }
            }
        }
    }
}
