package com.fake.money_lock

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.fake.money_lock.data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge the traditional way
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_login)

        // Apply system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get views
        val userId = findViewById<EditText>(R.id.etLoginEmail)
        val password = findViewById<EditText>(R.id.etLoginPassword)
        val login = findViewById<Button>(R.id.btnLogin)
        val register = findViewById<Button>(R.id.btnRegister)

        login.setOnClickListener {
            val userIdText = userId.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if (userIdText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                val userDao = UserDatabase.getDatabase(applicationContext).userDao()

                lifecycleScope.launch {
                    val userEntity = withContext(Dispatchers.IO) {
                        userDao.login(userIdText, passwordText)
                    }

                    if (userEntity == null) {
                        Toast.makeText(applicationContext, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, HomeScreen::class.java).apply {
                            putExtra("name", userEntity.name)
                        }
                        startActivity(intent)
                    }
                }
            }
        }

        // Handle Register button click
        register.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}