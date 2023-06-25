package com.dokiwei.zshg.tool.ui.screen.mainnav

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.dokiwei.zshg.tool.data.MyRoute
import kotlinx.coroutines.flow.MutableStateFlow

class LaunchHomeViewModel : ViewModel() {
    private val _mainBottomBarSelectedIndex = mutableIntStateOf(0)
    val mainBottomBarSelectedIndex = _mainBottomBarSelectedIndex
    fun changeMainBottomBarSelectedIndex(index: Int) {
        _mainBottomBarSelectedIndex.intValue = index
    }

    private val _navBottomBar = MutableStateFlow(false)
    val navBottomBar = _navBottomBar
    fun changeBottomBar(route: String) {
        _navBottomBar.value = when (route) {
            MyRoute.MainRoute.TOOL_HOME -> true // 在此屏幕上，底部栏应显示
            MyRoute.MainRoute.INFORMATION_HOME -> true // 这里也是
            else -> false // 在所有其他情况下隐藏底部栏
        }
    }
}