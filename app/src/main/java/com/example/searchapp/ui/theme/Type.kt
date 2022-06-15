package com.example.searchapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.searchapp.R

private val cantarell = FontFamily(
    Font(R.font.cantarell)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily  = cantarell,
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )
)