package lat.pam.hipudapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<MaterialButton>(R.id.btn_login)

        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val inputUser = etUsername.text.toString().trim()
            val inputPass = etPassword.text.toString().trim()

            val savedUser = prefs.getString("USERNAME", null)
            val savedPass = prefs.getString("PASSWORD", null)

            // ❌ BELUM REGISTER
            if (savedUser == null || savedPass == null) {
                Toast.makeText(this, "Akun belum terdaftar", Toast.LENGTH_SHORT).show()

                // BALIK KE SCREEN 2 (AuthActivity)
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // ❌ SALAH USERNAME / PASSWORD
            if (inputUser != savedUser || inputPass != savedPass) {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()

                // BALIK KE SCREEN 2
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // ✅ LOGIN BERHASIL
            Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
