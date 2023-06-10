package com.dokiwei.zshg.tool.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.isPopupLayout

@Preview
@Composable
fun InformationScreen(){
    Box(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
    )
}
