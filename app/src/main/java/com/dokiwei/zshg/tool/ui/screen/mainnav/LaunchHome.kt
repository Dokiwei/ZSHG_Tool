package com.dokiwei.zshg.tool.ui.screen.mainnav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dokiwei.zshg.tool.data.MyRoute
import com.dokiwei.zshg.tool.ui.animation.enterScreenAnim
import com.dokiwei.zshg.tool.ui.animation.enterSubScreenAnim
import com.dokiwei.zshg.tool.ui.animation.exitScreenAnim
import com.dokiwei.zshg.tool.ui.animation.exitSubScreenAnim
import com.dokiwei.zshg.tool.ui.screen.mainnav.information.InformationScreen
import com.dokiwei.zshg.tool.ui.screen.mainnav.tools.ToolsScreen
import com.dokiwei.zshg.tool.ui.screen.mainnav.tools.heirloom.HeirloomScreen
import com.dokiwei.zshg.tool.ui.screen.mainnav.tools.potential.PotentialCalculationScreen
import com.dokiwei.zshg.tool.ui.screen.start.StartScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LaunchHomeScreen() {
    val viewModel: LaunchHomeViewModel = viewModel()
    val navController = rememberAnimatedNavController()
    val showBottomBar by viewModel.navBottomBar.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()//获取当前的页面状态
    navBackStackEntry?.destination?.route?.let { viewModel.changeBottomBar(it) }
    Scaffold(
        bottomBar = {
            AnimatedBottomBar(navController, showBottomBar)
        }
    ) {
        MainNavHost(it, navController)
    }
}


/*
* NavHost定义
*/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(innerPadding: PaddingValues, navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "启动页",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(//动画启动页
            route = "启动页",
            enterTransition = enterScreenAnim(),
            exitTransition = exitScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            StartScreen(navController)
        }
        composable(//工具页 selectedItem==0
            MyRoute.Home.route,
            enterTransition = enterScreenAnim(),
            exitTransition = exitScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            ToolsScreen(navController)
        }
        composable(//信息页 selectedItem==1
            MyRoute.Information.route,
            enterTransition = enterScreenAnim(),
            exitTransition = exitScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            InformationScreen()
        }
        composable(//二级页面 工具/潜力计算页面
            MyRoute.ToolRoute.POTENTIAL_CALCULATION,
            enterTransition = enterSubScreenAnim(),
            exitTransition = exitSubScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            PotentialCalculationScreen {
                navController.navigate(MyRoute.Home.route)
            }
        }
        composable(//二级页面 工具/传家宝页面
            MyRoute.ToolRoute.HEIRLOOM,
            enterTransition = enterSubScreenAnim(),
            exitTransition = exitSubScreenAnim(),
            popEnterTransition = enterScreenAnim(),
            popExitTransition = exitScreenAnim()
        ) {
            HeirloomScreen()
        }
    }
}

/*
动画隐藏显示底部导航栏
*/
@Composable
fun AnimatedBottomBar(
    navController: NavHostController,
    showBottomBar: Boolean
) {
    val items = listOf(
        MyRoute.Home,
        MyRoute.Information
    )
    AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        content = {
            Box {
                MyNavigationBottomBar(items, navController)
            }
        }
    )
}


/*
* 底部导航栏UI
* */
@Composable
fun MyNavigationBottomBar(
    items: List<MyRoute>,
    navController: NavHostController
) {
    val viewModel: LaunchHomeViewModel = viewModel()
    navController.addOnDestinationChangedListener { _, destination, _ ->
        MyRoute.MainRoute.apply {
            when (destination.route) {
                HOME -> {
                    viewModel.changeMainBottomBarSelectedIndex(0)
                }

                INFORMATION -> {
                    viewModel.changeMainBottomBarSelectedIndex(1)
                }
            }
        }
    }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painterResource(if (viewModel.mainBottomBarSelectedIndex.intValue == index) item.idResFill else item.idRes),
                            item.route,
                            Modifier.size(24.dp),
                            tint = if (viewModel.mainBottomBarSelectedIndex.intValue == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        )
                    }
                },
                label = {
                    Text(
                        text = item.route,
                        textAlign = TextAlign.Center,
                    )
                },
                alwaysShowLabel = true,
                selected = viewModel.mainBottomBarSelectedIndex.intValue == index,
                onClick = {
                    viewModel.mainBottomBarSelectedIndex.intValue = index
                    navController.navigate(item.route) {
                        // 弹出到图表的起始目的地
                        // 避免建立大量目标
                        // 在用户选择项目时的后退堆栈上
                        popUpTo(MyRoute.Home.route) {
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