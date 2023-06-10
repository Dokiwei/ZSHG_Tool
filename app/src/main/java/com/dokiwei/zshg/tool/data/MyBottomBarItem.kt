package com.dokiwei.zshg.tool.data

import com.dokiwei.zshg.tool.R


sealed class MyBottomBarItem(val title: String, val idRes: Int, val idResFill: Int){
    object Home: MyBottomBarItem("首页", R.drawable.ic_tools,R.drawable.ic_tools_fill)
    object Information: MyBottomBarItem("资料", R.drawable.ic_book,R.drawable.ic_book_fill)
}
