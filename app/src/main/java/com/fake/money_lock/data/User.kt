package com.fake.money_lock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int = 0, // Account ID (auto-generated)

    @ColumnInfo(name = "name")
    val name: String, // User name

    @ColumnInfo(name = "email")
    val email: String, // User email address

    @ColumnInfo(name = "password")
    val password: String, // User password

    @ColumnInfo(name = "profile_image_path")
    val profileImagePath: String? = null, // Local path to the profile image

    @ColumnInfo(name = "budget_goal")
    val budgetGoal: Double = 0.0, // Monthly budget goal

    @ColumnInfo(name = "amount_spent")
    val amountSpent: Double = 0.0 // Running total of amount spent
)
