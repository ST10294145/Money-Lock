package com.fake.money_lock

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Apply system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Match IDs from XML
        val userId = findViewById<EditText>(R.id.etLoginEmail)
        val password = findViewById<EditText>(R.id.etLoginPassword)
        val login = findViewById<Button>(R.id.btnLogin)

        // Login click listener
        login.setOnClickListener {
            // TODO: Handle login logic here
        }
    }
}
