package com.fake.money_lock

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.firestore.FirebaseFirestore



class AnalyticsActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        barChart = findViewById(R.id.barChartCategory)

        loadExpenseData()
    }

    private fun loadExpenseData() {
        // Replace "expenses" with your Firestore collection name
        firestore.collection("expenses")
            .get()
            .addOnSuccessListener { result ->
                val categoryMap = mutableMapOf<String, Float>()

                for (doc in result) {
                    val category = doc.getString("category") ?: "Other"
                    val amount = doc.getDouble("amount")?.toFloat() ?: 0f
                    categoryMap[category] = categoryMap.getOrDefault(category, 0f) + amount
                }

                showBarChart(categoryMap)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun showBarChart(categoryMap: Map<String, Float>) {
        val entries = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()

        categoryMap.entries.forEachIndexed { index, entry ->
            entries.add(BarEntry(index.toFloat(), entry.value))
            labels.add(entry.key)
        }

        val dataSet = BarDataSet(entries, "Expenses by Category")
        dataSet.colors = listOf(Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA)
        val data = BarData(dataSet)

        barChart.data = data
        barChart.setFitBars(true)

        val description = Description()
        description.text = "Spending by Category"
        barChart.description = description

        barChart.invalidate()
    }
}
