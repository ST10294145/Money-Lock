package com.fake.money_lock.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,   // ✅ Use String here
    val password: String
)
