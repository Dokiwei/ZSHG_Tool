package com.dokiwei.zshg.tool.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dokiwei.zshg.tool.data.MyBottomBarItem
import com.dokiwei.zshg.tool.data.MyToolsItem
import com.dokiwei.zshg.tool.ui.animation.enterScreenAnim
import com.dokiwei.zshg.tool.ui.animation.enterSubScreenAnim
import com.dokiwei.zshg.tool.ui.animation.exitScreenAnim
import com.dokiwei.zshg.tool.ui.animation.exitSubScreenAnim
import com.dokiwei.zshg.tool.ui.viewmodel.MyViewModel
import com.dokiwei.zshg.tool.ui.screen.tools.HeirloomScreen
import com.dokiwei.zshg.tool.ui.screen.tools.PotentialCalculationScreen
import com.dokiwei.zshg.tool.ui.component.MyNavigationBottomBar
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LaunchHomeScreen() {
    val viewModel: MyViewModel = viewModel()
    val navController = rememberAnimatedNavController()

    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    showBottomBar = when (navBackStackEntry?.destination?.route) {
        MyBottomBarItem.Home.title -> true // 在此屏幕上，底部栏应显示
        MyBottomBarItem.Information.title -> true // 这里也是
        else -> false // 在所有其他情况下隐藏底部栏
    }
    Scaffold(
            bottomBar = {
                  BottomScreenNavController(navController, viewModel, showBottomBar)
            }
    ) { innerPadding ->
        BottomScreenNavHost(innerPadding, navController)
    }
}


//初始化NavHost
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomScreenNavHost(innerPadding: PaddingValues, navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = MyBottomBarItem.Home.title) {
        composable(
            MyBottomBarItem.Home.title,
            enterTransition = enterScreenAnim(),
            exitTransition = exitScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            ToolsScreen(navController)//工具页 selectedItem==0
        }
        composable(
            MyBottomBarItem.Information.title,
            enterTransition = enterScreenAnim(),
            exitTransition = exitScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            InformationScreen()//信息页 selectedItem==1
        }
        composable(
            MyToolsItem.PotentialCalculation.title,
            enterTransition = enterSubScreenAnim(),
            exitTransition = exitSubScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            PotentialCalculationScreen {
                navController.navigate(MyBottomBarItem.Home.title)
            }//潜力计算页
        }
        composable(
            MyToolsItem.Heirloom.title,
            enterTransition = enterSubScreenAnim(),
            exitTransition = exitSubScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            HeirloomScreen()//传家宝页
        }
    }
}

@Composable
fun BottomScreenNavController(navController: NavHostController, viewModel: MyViewModel, showBottomBar : Boolean) {
    val items = listOf(
        MyBottomBarItem.Home,
        MyBottomBarItem.Information
    )
    AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        content = {
            Box {
                MyNavigationBottomBar(viewModel, items, navController)
            }
        }
    )
}

@Preview
@Composable
fun PreviewLaunchHome(){
    LaunchHomeScreen()
}