package com.example.nida // Ganti dengan package name aplikasi Anda

import android.content.res.AssetFileDescriptor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import com.example.nida.R
class AnemiaActivity : AppCompatActivity() {

    // Komponen UI
    private lateinit var actSex: AutoCompleteTextView
    private lateinit var etRed: TextInputEditText
    private lateinit var etGreen: TextInputEditText
    private lateinit var etBlue: TextInputEditText
    private lateinit var etHb: TextInputEditText
    private lateinit var btnCheck: Button
    private lateinit var tvResult: TextView

    private var tflite: Interpreter? = null

    companion object {
        private const val TAG = "AnemiaActivity"

        // =========================================================================================
        // TODO: PENTING! Ganti nilai-nilai placeholder di bawah ini dengan nilai dari file scaler.pkl Anda.
        // Anda bisa mendapatkan nilai-nilai ini dengan menjalankan skrip Python berikut:
        //
        // import joblib
        // scaler = joblib.load('scaler.pkl')
        // print(f"Mean: {scaler.mean_.tolist()}")   // Salin output ini ke MEAN_VALUES
        // print(f"Scale: {scaler.scale_.tolist()}") // Salin output ini ke SCALE_VALUES
        // =========================================================================================
        private val MEAN_VALUES = floatArrayOf(0.50f, 35.5f, 30.5f, 35.0f, 13.5f) // CONTOH! GANTI NILAI INI
        private val SCALE_VALUES = floatArrayOf(0.5f, 5.0f, 5.0f, 5.0f, 2.0f)     // CONTOH! GANTI NILAI INI

        // Berdasarkan label_encoder.pkl dan logika di skrip Python Anda
        // Indeks 0 -> Tidak Anemia, Indeks 1 -> Anemia
        private val LABELS = arrayOf("Hasil: Tidak Berisiko Mengalami Anemia", "Hasil: Berisiko Mengalami Anemia")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anemia)

        initializeUI()

        try {
            tflite = Interpreter(loadModelFile())
        } catch (e: Exception) {
            Log.e(TAG, "Error loading TFLite model", e)
            Toast.makeText(this, "Gagal memuat model. Aplikasi tidak dapat berfungsi.", Toast.LENGTH_LONG).show()
            btnCheck.isEnabled = false
        }

        btnCheck.setOnClickListener { performInference() }
    }

    private fun initializeUI() {
        actSex = findViewById(R.id.actSex)
        etRed = findViewById(R.id.etRed)
        etGreen = findViewById(R.id.etGreen)
        etBlue = findViewById(R.id.etBlue)
        etHb = findViewById(R.id.etHb)
        btnCheck = findViewById(R.id.btnCheck)
        tvResult = findViewById(R.id.tvResult)

        val sexes = arrayOf("Female", "Male")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sexes)
        actSex.setAdapter(adapter)
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor: AssetFileDescriptor = this.assets.openFd("anemia_diagnosis.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun performInference() {
        if (!validateInputs()) {
            return
        }

        val inputFeatures = getInputFeatures() ?: return

        // Lakukan scaling
        for (i in inputFeatures.indices) {
            inputFeatures[i] = (inputFeatures[i] - MEAN_VALUES[i]) / SCALE_VALUES[i]
        }

        val inputBuffer = ByteBuffer.allocateDirect(1 * 5 * 4).apply {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().put(inputFeatures)
        }

        // ====================== PERUBAHAN DI SINI ======================
        // 1. Ubah bentuk output agar sesuai dengan model [1, 1]
        val output = Array(1) { FloatArray(1) }
        // =============================================================

        tflite?.run(inputBuffer, output) ?: run {
            Toast.makeText(this, "Model belum siap.", Toast.LENGTH_SHORT).show()
            return
        }

        // ====================== PERUBAHAN DI SINI ======================
        // 2. Ubah cara membaca hasil prediksi
        // Jika output > 0.5, anggap sebagai kelas 1 (Anemia), selain itu kelas 0
        val predictedIndex = if (output[0][0] > 0.5f) 1 else 0
        // =============================================================

        val resultText = LABELS[predictedIndex]
        displayResult(resultText, predictedIndex)
    }

    private fun validateInputs(): Boolean {
        if (actSex.text.toString().isEmpty() ||
            etRed.text.toString().isEmpty() ||
            etGreen.text.toString().isEmpty() ||
            etBlue.text.toString().isEmpty() ||
            etHb.text.toString().isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun getInputFeatures(): FloatArray? {
        return try {
            floatArrayOf(
                if (actSex.text.toString() == "Male") 1.0f else 0.0f,
                etRed.text.toString().toFloat(),
                etGreen.text.toString().toFloat(),
                etBlue.text.toString().toFloat(),
                etHb.text.toString().toFloat()
            )
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Pastikan semua input angka valid.", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun displayResult(result: String, predictedIndex: Int) {
        tvResult.visibility = View.VISIBLE
        tvResult.text = result

        if (predictedIndex == 1) {
            tvResult.setBackgroundColor(ContextCompat.getColor(this, R.color.red_light))
            tvResult.setTextColor(ContextCompat.getColor(this, R.color.red_dark))
        } else {
            tvResult.setBackgroundColor(ContextCompat.getColor(this, R.color.green_light))
            tvResult.setTextColor(ContextCompat.getColor(this, R.color.green_dark))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tflite?.close()
    }
}
