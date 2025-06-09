package com.fake.money_lock

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.fake.money_lock.data.BudgetGoalViewModel
import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.Position
import android.graphics.Color
import java.util.concurrent.TimeUnit



class RewardsActivity : AppCompatActivity() {

    private lateinit var viewModel: BudgetGoalViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        val konfettiView = findViewById<KonfettiView>(R.id.konfettiView)

        konfettiView.start(
            Party(
                angle = 270,
                spread = 360,
                speed = 10f,
                maxSpeed = 30f,
                damping = 0.9f,
                colors = listOf(Color.YELLOW, Color.GREEN, Color.MAGENTA),
                emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(100),
                position = Position.Relative(0.5, 0.0),
            )
        )


        val imgTrophy = findViewById<ImageView>(R.id.imgTrophy)
        val tvCongrats = findViewById<TextView>(R.id.tvCongrats)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)

        viewModel = ViewModelProvider(this)[BudgetGoalViewModel::class.java]

        val userId = 1 // Replace with dynamic userId if possible
        val month = "2025-06"

        viewModel.getGoalForUserAndMonth(userId, month).observe(this) { goal ->
            if (goal != null) {
                val budget = goal.monthlyBudget
                viewModel.getExpensesForUser(userId).observe(this) { expenses ->
                    val spent = expenses.sumOf { it.amount }
                    if (spent <= budget) {
                        // Show reward
                        imgTrophy.visibility = View.VISIBLE
                        tvCongrats.visibility = View.VISIBLE
                        tvMessage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
