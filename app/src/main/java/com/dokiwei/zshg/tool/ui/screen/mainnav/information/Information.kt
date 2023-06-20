package com.dokiwei.zshg.tool.ui.screen.mainnav.information


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dokiwei.zshg.tool.data.MyRoute
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(navController: NavHostController) {
    val items = listOf(
        MyRoute.InformationRoute.WEAPONS,
        MyRoute.InformationRoute.OCCUPATION,
        MyRoute.InformationRoute.LINEAGE,
        MyRoute.InformationRoute.COUNTRY,
        MyRoute.InformationRoute.IDOL
    )
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MyCenterAlignedTopAppBar(MyRoute.InformationRoute.STRATEGY)
            items.forEach { item ->
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
fun PreviewScreen(){
    val navController = rememberNavController()
    InformationScreen(navController)
}
