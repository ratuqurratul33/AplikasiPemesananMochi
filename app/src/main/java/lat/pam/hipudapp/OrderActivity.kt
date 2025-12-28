package lat.pam.hipudapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)

        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Greeting
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val userName = "Nazwa" // Ganti dengan data dari login
        tvTitle.text = "Halo $userName,"

        // ✅ Rekap pesanan (dummy dulu)
        val tvOrder = findViewById<TextView>(R.id.tv_order)
        tvOrder.text = """
            Pesanan saya:
            - Mochi Strawberry
            - Mochi Matcha
            - Mochi Oreo
        """.trimIndent()

        // ✅ Setup Tombol Kirim
        val btnKirim = findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_kirim)
        btnKirim.setOnClickListener {
            // Pindah ke halaman Success/Confirmation
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
        }

        // ✅ Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // Set item aktif (order)
        bottomNav.selectedItemId = R.id.nav_order

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // ⭐ PENTING: Tutup OrderActivity agar tidak menumpuk
                    true
                }
                R.id.nav_order -> {
                    // Sudah di Order, tidak perlu action
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish() // ⭐ PENTING: Tutup OrderActivity agar tidak menumpuk
                    true
                }
                else -> false
            }
        }
    }
}