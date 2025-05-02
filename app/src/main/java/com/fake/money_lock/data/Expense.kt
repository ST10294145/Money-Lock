package com.fake.money_lock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expense_id")
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,  // Foreign key reference to User

    val category: String,
    val amount: Double,
    val date: String, // You can use String (e.g., "2024-05-02") or Date with a type converter
    val note: String?
)
