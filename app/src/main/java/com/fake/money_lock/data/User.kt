package com.fake.money_lock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") // Custom column name
    val id: Int, // Account ID

    @ColumnInfo(name = "user_name") // Custom column name
    val name: String, // User name

    @ColumnInfo(name = "user_email") // Custom column name
    val email: String, // User email address

    @ColumnInfo(name = "user_password") // Custom column name
    val password: String // User password
)
