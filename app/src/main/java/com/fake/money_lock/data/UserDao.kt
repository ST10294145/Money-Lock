package com.fake.money_lock.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    // Insert a new user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    // Get all users
    @Query("SELECT * FROM user_table ORDER BY user_id ASC")
    fun readAllData(): LiveData<List<User>>

    // Get user by email
    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // Login: Match email and password
    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    // Update profile image path
    @Query("UPDATE user_table SET profile_image_path = :imagePath WHERE user_id = :userId")
    suspend fun updateProfileImage(userId: Int, imagePath: String)

    // Update budget goal
    @Query("UPDATE user_table SET budget_goal = :budgetGoal WHERE user_id = :userId")
    suspend fun updateBudgetGoal(userId: Int, budgetGoal: Double)

    // Update amount spent
    @Query("UPDATE user_table SET amount_spent = :amountSpent WHERE user_id = :userId")
    suspend fun updateAmountSpent(userId: Int, amountSpent: Double)
}
