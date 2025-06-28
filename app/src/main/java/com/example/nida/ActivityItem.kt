package com.example.nida

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nida.ui.theme.DinaTheme

// Model data untuk setiap item dalam daftar (Tidak berubah)
data class ActivityItem(
    val title: String,
    val description: String,
    val targetActivity: Class<*>,
    val icon: ImageVector,
    val gradientColors: List<Color>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val activities = listOf(
        ActivityItem(
            title = "Cek Risiko Anemia",
            description = "Mendiagnosis risiko anemia berdasarkan data darah dan memberikan rekomendasi kesehatan.",
            targetActivity = AnemiaActivity::class.java,
            icon = Icons.Default.Favorite,
            gradientColors = listOf(Color(0xFFFF6B6B), Color(0xFFFF8E8E), Color(0xFFFFB3B3))
        ),
        ActivityItem(
            title = "Tentang Aplikasi",
            description = "Informasi lengkap mengenai aplikasi, pengembang, dan tujuan pembuatan.",
            targetActivity = AboutActivity::class.java,
            icon = Icons.Default.Info,
            gradientColors = listOf(Color(0xFF4ECDC4), Color(0xFF6EDDD6), Color(0xFF8EE5DE))
        ),
        ActivityItem(
            title = "Data Set",
            description = "Lihat dan pelajari dataset yang digunakan dalam model prediksi anemia.",
            targetActivity = DatasetActivity::class.java,
            icon = Icons.Default.Storage,
            gradientColors = listOf(Color(0xFF45B7D1), Color(0xFF5FC3D7), Color(0xFF79CFDD))
        ),
        ActivityItem(
            title = "Info Fitur",
            description = "Penjelasan detail setiap fitur dan cara penggunaannya dalam aplikasi.",
            targetActivity = FeaturesActivity::class.java,
            icon = Icons.Default.Settings,
            gradientColors = listOf(Color(0xFFFFA726), Color(0xFFFFB74D), Color(0xFFFFC774))
        ),
        ActivityItem(
            title = "Arsitektur Model",
            description = "Tahapan pengembangan dan arsitektur model machine learning untuk prediksi.",
            targetActivity = ModelActivity::class.java,
            icon = Icons.Default.Psychology,
            gradientColors = listOf(Color(0xFFAB47BC), Color(0xFFBA68C8), Color(0xFFC976D4))
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "NIDA - Anemia Predictor",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF667EEA) // Warna TopAppBar tetap
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    // Latar belakang utama sedikit lebih lembut
                    Color(0xFFF8F9FA)
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // ============== AWAL PERUBAHAN HEADER ==============
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    // Menggunakan warna putih solid yang indah
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    // Elevasi untuk memberikan bayangan yang lembut
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth() // Pastikan Column mengisi Box
                        ) {
                            // Container ikon dengan latar belakang abu-abu muda
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Color(0xFFF0F2F5) // Warna latar ikon yang serasi
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.save),
                                    contentDescription = "App Logo",
                                    // Gunakan warna yang kontras dengan latar belakangnya
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(70.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Teks dengan warna hitam untuk kontras yang baik
                            Text(
                                text = "Anemia Predictor",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black // Tulisan menjadi hitam
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Prediksi Resiko Anemia  dengan AI",
                                fontSize = 16.sp,
                                color = Color(0xFF4A5568) // Warna abu-abu tua yang elegan
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Selamat datang! Pilih menu untuk memulai eksplorasi fitur aplikasi yang tersedia.",
                                fontSize = 14.sp,
                                color = Color(0xFF718096), // Warna abu-abu untuk deskripsi
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
                // ============== AKHIR PERUBAHAN HEADER ==============
            }

            items(activities) { activityItem ->
                ActivityCard(
                    activityItem = activityItem,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    val intent = Intent(context, activityItem.targetActivity)
                    context.startActivity(intent)
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// Composable ActivityCard tidak perlu diubah
@Composable
fun ActivityCard(
    activityItem: ActivityItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = activityItem.gradientColors
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = activityItem.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = activityItem.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = activityItem.description,
                    fontSize = 14.sp,
                    color = Color(0xFF718096),
                    lineHeight = 20.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF7FAFC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFF4A5568),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DinaTheme {
        MainScreen()
    }
}

// Pastikan Anda memiliki resource drawable bernama 'save.xml' di folder res/drawable
// Contoh isi file 'save.xml' (Anda bisa menggantinya dengan ikon SVG Anda):
/*
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
  <path
      android:fillColor="#000000"
      android:pathData="M17,3H5C3.89,3 3,3.9 3,5V19C3,20.1 3.89,21 5,21H19C20.1,21 21,20.1 21,19V7L17,3M19,19H5V5H16.17L19,7.83V19M12,12C10.34,12 9,13.34 9,15S10.34,18 12,18 15,16.66 15,15 13.66,12 12,12M6,6H15V10H6V6Z"/>
</vector>
*/