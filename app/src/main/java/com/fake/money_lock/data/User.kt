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
    val name: String,    // User name

    @ColumnInfo(name = "email")
    val email: String,   // User email address

    @ColumnInfo(name = "password")
    val password: String, // User password

    @ColumnInfo(name = "profile_image_path")
    val profileImagePath: String? = null // Path to the profile image stored locally (internal storage)
)
