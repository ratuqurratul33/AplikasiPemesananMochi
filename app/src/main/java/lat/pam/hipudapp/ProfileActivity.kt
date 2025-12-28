package lat.pam.hipudapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.hide()

        // =========================
        // BOTTOM NAVIGATION
        // =========================
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Set item aktif (profile)
        bottomNav.selectedItemId = R.id.nav_profile

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // ⭐ PENTING: Tutup ProfileActivity agar tidak menumpuk
                    true
                }
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java)
                    startActivity(intent)
                    finish() // ⭐ PENTING: Tutup ProfileActivity agar tidak menumpuk
                    true
                }
                R.id.nav_profile -> {
                    // Sudah di Profile, tidak perlu action
                    true
                }
                else -> false
            }
        }

        // =========================
        // DATA USER
        // =========================
        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tvPassword = findViewById<TextView>(R.id.tv_password)

        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        val username = prefs.getString("USERNAME", "User")
        val password = prefs.getString("PASSWORD", "-")

        tvUsername.text = username
        tvPassword.text = password
    }
}