package com.example.nida

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Temukan tombol berdasarkan ID
        val masukButton: Button = findViewById(R.id.btn_masuk_aplikasi)

        // Atur OnClickListener untuk tombol
        masukButton.setOnClickListener {
            // Buat Intent untuk memulai MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Selesaikan WelcomeActivity agar tidak bisa kembali dengan tombol back
            finish()
        }
    }
}