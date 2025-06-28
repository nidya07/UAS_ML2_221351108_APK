package com.example.nida

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottie_animation_view)

        // Tambahkan listener untuk mendeteksi kapan animasi selesai
        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Animasi dimulai
            }

            override fun onAnimationEnd(animation: Animator) {
                // Animasi selesai, pindah ke WelcomeActivity
                // ----> PERUBAHAN DI BARIS INI <----
                startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                finish() // Tutup activity ini agar tidak bisa kembali
            }

            override fun onAnimationCancel(animation: Animator) {
                // Jika animasi dibatalkan, mungkin juga arahkan ke activity selanjutnya
                startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Animasi diulang
            }
        })
    }
}