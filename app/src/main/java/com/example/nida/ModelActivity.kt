package com.example.nida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ModelActivity : AppCompatActivity() {

    // Menambahkan imageResId untuk menampung ID gambar dari drawable
    data class ModelStep(
        val titleResId: Int,
        val descriptionResId: Int,
        val imageResId: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.model_title)

        populateModelSteps()
    }

    private fun populateModelSteps() {
        val container: LinearLayout = findViewById(R.id.model_steps_container)
        val inflater = LayoutInflater.from(this)

        // Hubungkan setiap langkah dengan gambar yang sesuai dari folder drawable
        // Ganti R.drawable.nama_file_anda dengan nama file gambar Anda
        val steps = listOf(
            ModelStep(R.string.model_step1_title, R.string.model_step1_desc, R.drawable.data1),
            ModelStep(R.string.model_step2_title, R.string.model_step2_desc, R.drawable.ss1),
            ModelStep(R.string.model_step3_title, R.string.model_step3_desc, R.drawable.data3),
            ModelStep(R.string.model_step4_title, R.string.model_step4_desc, R.drawable.data4)
        )

        steps.forEach { step ->
            val stepView = inflater.inflate(R.layout.item_screenshot_card, container, false)

            val titleView: TextView = stepView.findViewById(R.id.screenshot_title)
            val descriptionView: TextView = stepView.findViewById(R.id.screenshot_description)
            // Tambahkan referensi untuk ImageView
            val imageView: ImageView = stepView.findViewById(R.id.screenshot_image)

            titleView.setText(step.titleResId)
            descriptionView.setText(step.descriptionResId)
            // Atur gambar untuk ImageView
            imageView.setImageResource(step.imageResId)

            container.addView(stepView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
