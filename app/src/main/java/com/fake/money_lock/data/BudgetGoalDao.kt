package com.fake.money_lock.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: BudgetGoalEntity)

    @Update
    suspend fun updateGoal(goal: BudgetGoalEntity)

    @Delete
    suspend fun deleteGoal(goal: BudgetGoalEntity)

    @Query("SELECT * FROM budget_goal_table WHERE user_id = :userId AND month = :month LIMIT 1")
    fun getGoalForUserAndMonth(userId: Int, month: String): LiveData<BudgetGoalEntity?>
}
