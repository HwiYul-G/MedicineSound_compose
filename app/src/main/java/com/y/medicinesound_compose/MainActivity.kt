package com.y.medicinesound_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.y.medicinesound_compose.ui.MedicineSoundApp
import com.y.medicinesound_compose.ui.theme.MedicineSound_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicineSound_composeTheme {
                MedicineSoundApp()
            }
        }

    }
}


