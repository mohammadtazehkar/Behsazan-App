package com.example.behsaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface {
                    val sharedViewModel: SharedViewModel = viewModel()
                    BehsazNavHost(sharedViewModel)
                    //test for mammad branch
                }
            }
        }
    }

}
