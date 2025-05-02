package com.fake.money_lock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Add Expense entity and bump version to 2
@Database(entities = [User::class, Expense::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun expenseDao(): ExpenseDao  // New DAO for managing expenses

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
