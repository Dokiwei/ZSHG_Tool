package com.dokiwei.zshg.tool.ui.screen.start

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dokiwei.zshg.tool.data.MyRoute
import kotlinx.coroutines.delay

@Composable
fun StartScreen(navController: NavHostController) {
    var titleVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(1000)
        titleVisible = true
        delay(500)
        navController.navigate(MyRoute.Home.route){

        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = titleVisible,
            enter = expandVertically(expandFrom = Alignment.Bottom) + fadeIn(
                initialAlpha = 0.1f
            )
        ) {
            Text(
                text = "诸神助手",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
