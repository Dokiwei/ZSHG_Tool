package com.dokiwei.zshg.tool.ui.screen

import android.content.Context
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dokiwei.zshg.tool.data.MyToolsItem
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedTopAppBar
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val items = listOf(
        MyToolsItem.PotentialCalculation,
        MyToolsItem.Heirloom
    )
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MyCenterAlignedTopAppBar(MyToolsItem.Tool.title)
            items.forEach { item ->
                Card(
                    onClick = {
                        if (MyToolsItem.PotentialCalculation.title == item.title)
                            navController.navigate(MyToolsItem.PotentialCalculation.title)
                        else
                            navController.navigate(MyToolsItem.Heirloom.title)
                        try {
                            val output = context.openFileOutput("a.txt", Context.MODE_PRIVATE)
                            val writer = BufferedWriter(OutputStreamWriter(output))
                            writer.use {
                                it.write("666")
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    },
                    Modifier
                        .size(width = 180.dp, height = 100.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            item.title,
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
fun PreviewToolScreen(){
    val navController = rememberNavController()
    ToolsScreen(navController)
}