package com.example.behsaz.ui.models

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

data class TextInputData(
    val label: String,
    val imageResourceId: Int,
    val type: Int,
    val keyboardType: KeyboardType,
    val imedAction: ImeAction,

)
