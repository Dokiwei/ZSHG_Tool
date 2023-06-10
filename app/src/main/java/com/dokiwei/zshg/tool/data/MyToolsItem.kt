package com.dokiwei.zshg.tool.data

sealed class MyToolsItem(val title : String, val route : String){
    object Tool : MyToolsItem("工具","工具")
    object PotentialCalculation : MyToolsItem("潜力计算","潜力计算")
    object Heirloom : MyToolsItem("传家宝","传家宝")
}
