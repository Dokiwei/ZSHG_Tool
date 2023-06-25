package com.dokiwei.zshg.tool.ui.screen.mainnav.tools

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dokiwei.zshg.tool.data.MyRoute
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedTopAppBar
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(navController: NavHostController) {
    val backPressed = remember { mutableLongStateOf(0L) }
    val context = LocalContext.current

    BackHandler(onBack = {
        if (backPressed.longValue + 2000 > System.currentTimeMillis()) {
            exitProcess(0)
        } else {
            Toast.makeText(context, "再按一次退出", Toast.LENGTH_SHORT).show()
            backPressed.longValue = System.currentTimeMillis()
        }
    })
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MyCenterAlignedTopAppBar(MyRoute.ToolRoute.TOOL)
            listOf(
                MyRoute.ToolRoute.POTENTIAL_CALCULATION,
                MyRoute.ToolRoute.HEIRLOOM
            ).forEach { item ->
                Card(
                    onClick = {
                        navController.navigate(item)

                    },
                    Modifier
                        .size(width = 180.dp, height = 100.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            item,
                            Modifier.align(Alignment.Center),
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToolScreen() {
    val navController = rememberNavController()
    ToolsScreen(navController)
}