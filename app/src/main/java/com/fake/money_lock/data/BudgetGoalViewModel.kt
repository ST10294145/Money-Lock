package com.fake.money_lock.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BudgetGoalViewModel(application: Application) : AndroidViewModel(application) {

    private val budgetGoalDao = UserDatabase.getDatabase(application).budgetGoalDao()

    // LiveData for a specific user and month (can be exposed from a method)
    fun getGoalForUserAndMonth(userId: Int, month: String): LiveData<BudgetGoalEntity?> {
        return budgetGoalDao.getGoalForUserAndMonth(userId, month)
    }

    // Insert or update a goal
    fun setOrUpdateGoal(goal: BudgetGoalEntity) {
        viewModelScope.launch {
            budgetGoalDao.insertGoal(goal) // Since onConflict = REPLACE, this works for both insert/update
        }
    }

    // Delete a goal
    fun deleteGoal(goal: BudgetGoalEntity) {
        viewModelScope.launch {
            budgetGoalDao.deleteGoal(goal)
        }
    }
}