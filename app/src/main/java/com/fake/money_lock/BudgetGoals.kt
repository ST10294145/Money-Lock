package com.fake.money_lock

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fake.money_lock.data.BudgetGoalEntity
import com.fake.money_lock.data.BudgetGoalViewModel
import java.text.SimpleDateFormat
import java.util.*

class BudgetGoals : AppCompatActivity() {

    private lateinit var etGoalDescription: EditText
    private lateinit var etGoalAmount: EditText
    private lateinit var etSavingsGoal: EditText // Added for savings goal
    private lateinit var btnPickDeadline: Button
    private lateinit var btnSaveGoal: Button

    private val viewModel: BudgetGoalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_goals)

        // Initialize views
        etGoalDescription = findViewById(R.id.etGoalDescription)
        etGoalAmount = findViewById(R.id.etGoalAmount)
        etSavingsGoal = findViewById(R.id.etSavingsGoal) // Initialize savings goal input
        btnPickDeadline = findViewById(R.id.btnPickDeadline)
        btnSaveGoal = findViewById(R.id.btnSaveGoal)

        val userId = getLoggedInUserId()
        val currentMonth = getCurrentMonthString()

        // Observe and load existing budget goal for this user/month
        viewModel.getGoalForUserAndMonth(userId, currentMonth).observe(this) { goal ->
            goal?.let {
                etGoalDescription.setText(it.description) // Populate description
                etGoalAmount.setText(it.monthlyBudget.toString()) // Populate monthly budget
                etSavingsGoal.setText(it.savingsGoal.toString()) // Populate savings goal
            }
        }

        // Save or update goal
        btnSaveGoal.setOnClickListener {
            val description = etGoalDescription.text.toString()
            val goalAmount = etGoalAmount.text.toString().toDoubleOrNull()
            val savingsGoal = etSavingsGoal.text.toString().toDoubleOrNull()

            if (description.isEmpty() || goalAmount == null || savingsGoal == null) {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val goal = BudgetGoalEntity(
                userId = userId,
                month = currentMonth,
                description = description,
                monthlyBudget = goalAmount,
                savingsGoal = savingsGoal // Add savingsGoal here
            )

            viewModel.setOrUpdateGoal(goal)
            Toast.makeText(this, "Goal saved", Toast.LENGTH_SHORT).show()
        }

        // Deadline Picker logic
        btnPickDeadline.setOnClickListener {
            // Implement your deadline picker logic here (e.g., show DatePickerDialog)
        }
    }

    private fun getCurrentMonthString(): String {
        return SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())
    }

    private fun getLoggedInUserId(): Int {
        // Replace this with actual login logic later
        return 1
    }
}
