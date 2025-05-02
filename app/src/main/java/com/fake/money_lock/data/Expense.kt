package com.fake.money_lock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expense_id")
    val id: Int = 0, // Expense ID

    val userId: Int,          // Reference to the user
    val category: String,     // Category like "Food", "Rent"
    val amount: Double,       // Expense amount
    val date: String          // Date in format "yyyy-MM-dd"
)
