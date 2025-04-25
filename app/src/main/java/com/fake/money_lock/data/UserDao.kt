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
    // If there's a conflict (e.g. same primary key), ignore the new data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    // Retrieve all users from the database, ordered by their ID in ascending order
    // Returns LiveData so changes can be observed in real-time
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>
}