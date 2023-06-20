package com.dokiwei.zshg.tool.data

import com.dokiwei.zshg.tool.R

sealed class MyRoute(val route: String, val idRes: Int, val idResFill: Int) {
    object Home : MyRoute("首页", R.drawable.ic_tools, R.drawable.ic_tools_fill)
    object Information : MyRoute("资料", R.drawable.ic_book, R.drawable.ic_book_fill)
    object MainRoute {
        const val TOOL_HOME = "工具首页"
        const val INFORMATION_HOME = "攻略首页"
    }

    object ToolRoute {
        const val TOOL = "工具"
        const val POTENTIAL_CALCULATION = "潜力计算"
        const val HEIRLOOM = "传家宝"
    }
    object InformationRoute{
        const val STRATEGY="攻略"
        const val WEAPONS="武器"
        const val OCCUPATION="职业"
        const val LINEAGE="血统"
        const val COUNTRY="国家"
        const val IDOL="神像"
    }
}
