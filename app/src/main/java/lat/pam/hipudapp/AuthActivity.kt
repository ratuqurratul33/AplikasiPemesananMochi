package lat.pam.hipudapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()

        // 1. Tombol Register
        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            // Pindah ke halaman Register
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // 2. Tombol Login
        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            // Pindah ke halaman Login
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}