package lat.pam.hipudapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ PASTI PAKAI activity_success
        setContentView(R.layout.activity_succsess)

        val tvTitle = findViewById<TextView>(R.id.tv_success_title)
        val btnDone = findViewById<MaterialButton>(R.id.btn_done)

        // Ambil username dari SharedPreferences
        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val username = prefs.getString("USERNAME", "User")

        // Set teks Halo username
        tvTitle.text = "Halo $username,"

        btnDone.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }
    }
}
