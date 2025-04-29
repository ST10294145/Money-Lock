package com.fake.money_lock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object (DAO) for the User entity
@Dao
interface UserDao {

    // Insert a new user into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    // Retrieve all users from the database
    @Query("SELECT * FROM user_table ORDER BY user_id ASC")
    fun readAllData(): LiveData<List<User>>

    // Retrieve a single user by email
    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}
