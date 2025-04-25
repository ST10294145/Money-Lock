package com.fake.money_lock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define the Room database with the User entity and version number
// exportSchema = false disables exporting the schema into a folder (not needed for simple projects)
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    // Abstract function that provides access to DAO methods for User entity
    abstract fun userDao(): UserDao

    companion object {
        // Volatile ensures changes made to INSTANCE are immediately visible to all threads
        @Volatile
        private var INSTANCE: UserDatabase? = null

        // Returns the database instance (singleton)
        // Creates the database if it doesn't already exist
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create a new database instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // Use application context to avoid leaking activity context
                    UserDatabase::class.java,    // Class reference to the database
                    "user_database"              // Name of the database file
                )
                    .fallbackToDestructiveMigration() // Wipes and rebuilds database on version mismatch
                    .build()

                // Assign the newly created instance to the INSTANCE variable
                INSTANCE = instance
                instance
            }
        }
    }
}
