package com.fake.money_lock

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fake.money_lock.data.BudgetGoalViewModel
import com.fake.money_lock.data.ExpenseViewModel
import com.fake.money_lock.data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ViewBudget : AppCompatActivity() {

    private lateinit var tvBudgetTitle: TextView
    private lateinit var tvBudgetGoal: TextView
    private lateinit var tvBudgetSpent: TextView
    private lateinit var tvBudgetRemaining: TextView

    private val budgetViewModel: BudgetGoalViewModel by viewModels()
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_budget)

        tvBudgetTitle = findViewById(R.id.tvBudgetTitle)
        tvBudgetGoal = findViewById(R.id.tvBudgetGoal)
        tvBudgetSpent = findViewById(R.id.tvBudgetSpent)
        tvBudgetRemaining = findViewById(R.id.tvBudgetRemaining)

        loadBudgetData()
    }

    private fun loadBudgetData() {
        val userDao = UserDatabase.getDatabase(applicationContext).userDao()

        userDao.readAllData().observe(this@ViewBudget) { userList ->
            val user = userList.firstOrNull()

            if (user == null) {
                showError("User not found")
                return@observe
            }

            val userId = user.id
            val monthFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
            val currentMonth = monthFormat.format(Date())

            // Observe budget goal
            budgetViewModel.getGoalForUserAndMonth(userId, currentMonth).observe(this@ViewBudget) { budgetGoal ->
                if (budgetGoal == null) {
                    showError("No budget goal found for this month")
                } else {
                    // Observe expenses
                    expenseViewModel.expenses.observe(this@ViewBudget) { expenses ->
                        val totalSpent = expenses
                            .filter { it.userId == userId && it.date.startsWith(currentMonth) }
                            .sumOf { it.amount }

                        val remaining = budgetGoal.monthlyBudget - totalSpent

                        tvBudgetGoal.text = "Budget Goal: $${"%.2f".format(budgetGoal.monthlyBudget)}"
                        tvBudgetSpent.text = "Amount Spent: $${"%.2f".format(totalSpent)}"
                        tvBudgetRemaining.text = "Remaining Budget: $${"%.2f".format(remaining)}"
                    }
                }
            }
        }
    }


    private fun showError(message: String) {
        tvBudgetGoal.text = message
        tvBudgetSpent.text = ""
        tvBudgetRemaining.text = ""
    }
}
