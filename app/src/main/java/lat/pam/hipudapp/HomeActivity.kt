package lat.pam.hipudapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        // 1. Setup greeting
        val tvGreeting = findViewById<TextView>(R.id.tv_greeting)
        // contoh nama user (nanti bisa dari login)
        val userName = "Nazwa"
        tvGreeting.text = "Halo $userName,"

        // 2. Setup menu list
        val menuList = listOf(
            Menu("Mochi Strawberry", "Isian strawberry segar", R.drawable.mochi_strawberry),
            Menu("Mochi Choco", "Coklat lumer lembut", R.drawable.mochi_choco),
            Menu("Mochi Greentea", "Greentea wangi khas", R.drawable.mochi_greantea),
            Menu("Mochi Mangga", "Mangga manis segar", R.drawable.mochi_mangga),
            Menu("Mochi Red Velvet", "Red velvet creamy", R.drawable.mochi_redvelvet),
            Menu("Mochi Matcha", "Matcha premium Jepang", R.drawable.mochi_matcha),
            Menu("Mochi Kacang Choco", "Kacang & coklat crunchy", R.drawable.mochi_kacangchoco),
            Menu("Mochi Berry", "Campuran berry segar", R.drawable.mochi_berry),
            Menu("Mochi Blueberry", "Blueberry manis asam", R.drawable.mochi_bluberry),
            Menu("Mochi Oreo", "Oreo crumble favorit", R.drawable.mochi_oreo)
        )

        val rvMenu = findViewById<RecyclerView>(R.id.rv_menu)
        rvMenu.layoutManager = LinearLayoutManager(this)
        rvMenu.adapter = MenuAdapter(menuList)

        // 3. Setup Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_start)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Sudah di Home, tidak perlu action
                    true
                }
                R.id.nav_order -> {
                    // Pindah ke OrderActivity
                    startActivity(Intent(this, OrderActivity::class.java))
                    finish() // Menutup HomeActivity agar tidak menumpuk
                    true
                }
                R.id.nav_profile -> {
                    // Pindah ke ProfileActivity
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish() // Menutup HomeActivity agar tidak menumpuk
                    true
                }
                else -> false
            }
        }

        // 4. Set item aktif (home) di bottom nav
        bottomNav.selectedItemId = R.id.nav_home
    }
}