package lat.pam.hipudapp

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnRegister = findViewById<MaterialButton>(R.id.btn_register)

        // SharedPreferences untuk simpan data user
        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username, dan password wajib diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Simpan data
            prefs.edit()
                .putString("USERNAME", username)   // dipakai untuk greeting & profile
                .putString("PASSWORD", password)   // dipakai untuk validasi login
                .apply()

            Toast.makeText(
                this,
                "Register berhasil, silakan login",
                Toast.LENGTH_SHORT
            ).show()

            finish() // kembali ke Login / Auth
        }
    }
}
