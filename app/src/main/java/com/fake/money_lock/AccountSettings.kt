package com.fake.money_lock

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fake.money_lock.data.User
import com.fake.money_lock.data.UserDatabase
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AccountSettings : AppCompatActivity() {

    private lateinit var ivProfile: ImageView
    private lateinit var btnUploadProfile: Button
    private lateinit var btnLogout: Button

    private val PICK_IMAGE_REQUEST = 1
    private var userId: Int = 1 // Replace this with actual logic to get logged-in user

    private val userDao by lazy {
        UserDatabase.getDatabase(this).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        ivProfile = findViewById(R.id.ivProfile)
        btnUploadProfile = findViewById(R.id.btnUploadProfile)
        btnLogout = findViewById(R.id.btnLogout)

        // Fetch the user ID passed from the Login activity
        userId = intent.getIntExtra("user_id", -1) // Retrieve user ID from intent (you need to send it from Login Activity)

        loadProfileImage()

        btnUploadProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null) {
            val imageUri = data.data!!
            saveImageToInternalStorage(imageUri)?.let { savedPath ->
                ivProfile.setImageURI(Uri.fromFile(File(savedPath)))
                updateUserProfileImage(savedPath)
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val fileName = getFileName(uri)
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val file = File(filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileName(uri: Uri): String {
        var name = "temp_image_${System.currentTimeMillis()}.jpg"
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }

    private fun updateUserProfileImage(imagePath: String) {
        lifecycleScope.launch {
            userDao.updateProfileImage(userId, imagePath)
        }
    }

    private fun loadProfileImage() {
        lifecycleScope.launch {
            val user: User? = userDao.readAllData().value?.find { it.id == userId }
            user?.profileImagePath?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    ivProfile.setImageURI(Uri.fromFile(file))
                }
            }
        }
    }
}
