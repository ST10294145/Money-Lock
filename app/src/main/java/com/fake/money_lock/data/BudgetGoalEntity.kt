package com.fake.money_lock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_goal_table")
data class BudgetGoalEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "month")
    val month: String, // Format: "2025-05"

    @ColumnInfo(name = "monthly_budget")
    val monthlyBudget: Double,

    @ColumnInfo(name = "savings_goal")
    val savingsGoal: Double,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "deadline")
    val deadline: String? = null // New field for deadline (format: yyyy-MM-dd)
)
