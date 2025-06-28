package com.example.nida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FeaturesActivity : AppCompatActivity() {

    // Data class untuk menampung informasi deskripsi fitur
    data class FeatureDescription(
        val titleResId: Int,
        val typeResId: Int,
        val descriptionResId: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_features)

        // Setup ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.features_title)

        populateFeaturesList()
    }

    private fun populateFeaturesList() {
        val container: LinearLayout = findViewById(R.id.features_list_container)
        val inflater = LayoutInflater.from(this)

        val features = listOf(
            FeatureDescription(R.string.feature_title_sex, R.string.feature_type_sex, R.string.feature_desc_sex),
            FeatureDescription(R.string.feature_title_red, R.string.feature_type_pixel, R.string.feature_desc_red),
            FeatureDescription(R.string.feature_title_green, R.string.feature_type_pixel, R.string.feature_desc_green),
            FeatureDescription(R.string.feature_title_blue, R.string.feature_type_pixel, R.string.feature_desc_blue),
            FeatureDescription(R.string.feature_title_hb, R.string.feature_type_pixel, R.string.feature_desc_hb)
        )

        features.forEach { feature ->
            val cardView = inflater.inflate(R.layout.item_feature_description, container, false)

            val titleView: TextView = cardView.findViewById(R.id.feature_item_title)
            val typeView: TextView = cardView.findViewById(R.id.feature_item_type)
            val descriptionView: TextView = cardView.findViewById(R.id.feature_item_description)

            titleView.setText(feature.titleResId)
            typeView.setText(feature.typeResId)
            descriptionView.setText(feature.descriptionResId)

            container.addView(cardView)
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
