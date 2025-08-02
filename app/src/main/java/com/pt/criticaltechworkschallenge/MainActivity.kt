package com.pt.criticaltechworkschallenge

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.pt.criticaltechworkschallenge.ui.BiometricGate
import com.pt.criticaltechworkschallenge.ui.theme.CriticalTechWorksChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CriticalTechWorksChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BiometricGate()
                }
            }
        }
    }
}
