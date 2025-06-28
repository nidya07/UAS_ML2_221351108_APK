package com.example.nida
import androidx.activity.ComponentActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Setup ActionBar
        setupActionBar()

        // Setup animations
        setupAnimations()

    }

    private fun setupActionBar() {
        // Menambahkan tombol kembali (panah) di action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Mengatur judul action bar
        title = getString(R.string.about_title)

        // Optional: Customize action bar color or style
        supportActionBar?.elevation = 8f
    }

    private fun setupAnimations() {
        // App icon rotation animation
        val appIcon = findViewById<ImageView>(R.id.app_icon)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_slow)

        // Start animation when icon is clicked
        appIcon.setOnClickListener {
            appIcon.startAnimation(rotateAnimation)

            // Show easter egg message
            Snackbar.make(
                findViewById(android.R.id.content),
                "🩸 Dina - Deteksi Anemia dengan AI! 🤖",
                Snackbar.LENGTH_SHORT
            ).setAction("Cool!") {
                // Optional action
            }.show()
        }

        // Fade in animation for cards (optional)
        animateCardsEntrance()
    }

    private fun animateCardsEntrance() {
        // Optional: Add entrance animations for cards
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)

        // You can apply animations to cards here if needed
        // findViewById<MaterialCardView>(R.id.storyline_card)?.startAnimation(fadeIn)
    }



    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            // Handle error - show message if no browser available
            Snackbar.make(
                findViewById(android.R.id.content),
                "Tidak dapat membuka link. Pastikan browser tersedia.",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    // Fungsi ini akan dipanggil saat tombol kembali di action bar ditekan
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Add slide transition when going back
                finish()
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        // Custom back press animation
        super.onBackPressed()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}