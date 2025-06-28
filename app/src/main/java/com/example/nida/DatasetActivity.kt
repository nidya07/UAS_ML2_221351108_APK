package com.example.nida

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DatasetActivity : AppCompatActivity() {

    // Data class untuk menampung satu baris data
    data class SampleData(
        val sex: String,
        val red: String,
        val green: String,
        val blue: String,
        val hb: String,
        val anaemic: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataset)

        // Setup ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.dataset_title)

        // Panggil fungsi untuk mengisi tabel data
        populateDataTable()
        updateStatistics()
    }

    private fun populateDataTable() {
        val tableLayout: TableLayout = findViewById(R.id.data_table)

        // Tentukan Header Tabel
        val headers = listOf(
            "Sex", "Red", "Green", "Blue", "Hb", "Anaemic"
        )

        // Tentukan Contoh Data (berdasarkan dataset anemia)
        val sampleDataList = listOf(
            SampleData("F", "44.89", "30.45", "24.72", "11.72", "No"),
            SampleData("F", "44.11", "29.60", "26.29", "12.47", "No"),
            SampleData("F", "44.33", "30.38", "25.42", "8.02", "Yes"),
            SampleData("F", "43.02", "29.38", "27.41", "9.87", "Yes"),
            SampleData("M", "45.38", "29.41", "25.11", "12.15", "No"),
            SampleData("M", "42.85", "31.22", "25.93", "8.95", "Yes"),
            SampleData("F", "46.12", "28.75", "25.13", "13.20", "No"),
            SampleData("M", "43.67", "30.89", "25.44", "10.45", "Yes")
        )

        // Buat Baris Header dengan styling yang lebih baik
        val headerRow = TableRow(this)
        headerRow.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))

        headers.forEach { headerText ->
            val textView = createTableCell(headerText, isHeader = true)
            textView.setTextColor(ContextCompat.getColor(this, R.color.on_primary))
            headerRow.addView(textView)
        }
        tableLayout.addView(headerRow)

        // Buat Baris-baris Data dengan alternating colors
        sampleDataList.forEachIndexed { index, data ->
            val dataRow = TableRow(this)

            // Alternating row colors
            if (index % 2 == 0) {
                dataRow.setBackgroundColor(ContextCompat.getColor(this, R.color.surface_variant))
            } else {
                dataRow.setBackgroundColor(Color.WHITE)
            }

            // Add cells dengan styling
            dataRow.addView(createTableCell(data.sex, textColor = getSexColor(data.sex)))
            dataRow.addView(createTableCell(data.red))
            dataRow.addView(createTableCell(data.green))
            dataRow.addView(createTableCell(data.blue))
            dataRow.addView(createTableCell(data.hb, textColor = getHbColor(data.hb.toFloat())))
            dataRow.addView(createTableCell(data.anaemic, textColor = getAnaemicColor(data.anaemic)))

            tableLayout.addView(dataRow)
        }
    }

    /**
     * Fungsi bantuan untuk membuat satu sel (TextView) untuk tabel dengan styling yang lebih baik.
     */
    private fun createTableCell(
        text: String,
        isHeader: Boolean = false,
        textColor: Int = Color.BLACK
    ): TextView {
        return TextView(this).apply {
            this.text = text
            setPadding(24, 20, 24, 20)
            gravity = Gravity.CENTER
            textSize = if (isHeader) 14f else 13f

            if (isHeader) {
                setTypeface(null, Typeface.BOLD)
            } else {
                setTextColor(textColor)
            }

            // Add subtle border
            val drawable = GradientDrawable()
            drawable.setStroke(1, ContextCompat.getColor(this@DatasetActivity, R.color.outline))
            background = drawable

            // Make text selectable
            setTextIsSelectable(true)
        }
    }

    /**
     * Get color based on sex
     */
    private fun getSexColor(sex: String): Int {
        return when (sex) {
            "F" -> ContextCompat.getColor(this, R.color.pink_500)
            "M" -> ContextCompat.getColor(this, R.color.blue_500)
            else -> Color.BLACK
        }
    }

    /**
     * Get color based on Hemoglobin level
     */
    private fun getHbColor(hb: Float): Int {
        return when {
            hb < 10.0 -> ContextCompat.getColor(this, R.color.error) // Low - Red
            hb < 12.0 -> ContextCompat.getColor(this, R.color.orange_500) // Medium - Orange
            else -> ContextCompat.getColor(this, R.color.green_500) // Good - Green
        }
    }

    /**
     * Get color based on anaemic status
     */
    private fun getAnaemicColor(anaemic: String): Int {
        return when (anaemic) {
            "Yes" -> ContextCompat.getColor(this, R.color.error)
            "No" -> ContextCompat.getColor(this, R.color.green_500)
            else -> Color.BLACK
        }
    }

    /**
     * Update statistics in the statistics card
     */
    private fun updateStatistics() {
        val sampleDataList = listOf(
            SampleData("F", "44.89", "30.45", "24.72", "11.72", "No"),
            SampleData("F", "44.11", "29.60", "26.29", "12.47", "No"),
            SampleData("F", "44.33", "30.38", "25.42", "8.02", "Yes"),
            SampleData("F", "43.02", "29.38", "27.41", "9.87", "Yes"),
            SampleData("M", "45.38", "29.41", "25.11", "12.15", "No"),
            SampleData("M", "42.85", "31.22", "25.93", "8.95", "Yes"),
            SampleData("F", "46.12", "28.75", "25.13", "13.20", "No"),
            SampleData("M", "43.67", "30.89", "25.44", "10.45", "Yes")
        )

        val totalSamples = sampleDataList.size
        val anaemicCount = sampleDataList.count { it.anaemic == "Yes" }
        val healthyCount = sampleDataList.count { it.anaemic == "No" }

        findViewById<TextView>(R.id.tv_total_samples)?.text = totalSamples.toString()
        findViewById<TextView>(R.id.tv_anaemic_count)?.text = anaemicCount.toString()
        findViewById<TextView>(R.id.tv_healthy_count)?.text = healthyCount.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}