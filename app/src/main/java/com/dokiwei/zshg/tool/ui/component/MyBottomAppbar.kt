package com.dokiwei.zshg.tool.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dokiwei.zshg.tool.data.MyBottomBarItem
import com.dokiwei.zshg.tool.ui.viewmodel.MyViewModel


@Composable
fun MyNavigationBottomBar(viewModel: MyViewModel, items: List<MyBottomBarItem>, navController: NavHostController) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painterResource(if (viewModel.selectedItem == index) item.idResFill else item.idRes),
                            item.title,
                            Modifier.size(24.dp),
                            tint = if (viewModel.selectedItem == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        textAlign = TextAlign.Center,
                    )
                },
                alwaysShowLabel = true,
                selected = viewModel.selectedItem == index,
                onClick = {
                    viewModel.selectedItem = index
                    viewModel.topBarTitle = item.title
                    navController.navigate(item.title) {
                        // 弹出到图表的起始目的地
                        // 避免建立大量目标
                        // 在用户选择项目时的后退堆栈上
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // 避免在以下情况下使用同一目标的多个副本
                        // 重新选择同一项目
                        launchSingleTop = true
                        // 重新选择以前选定的项目时恢复状态
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewNavigationBottomBar(){
    val navController = rememberNavController()
    val items = listOf(MyBottomBarItem.Home,MyBottomBarItem.Information)
    val viewModel : MyViewModel = viewModel()
    MyNavigationBottomBar(viewModel,items,navController)
}