package com.dokiwei.zshg.tool.ui.screen.tools

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dokiwei.zshg.tool.R
import com.dokiwei.zshg.tool.data.MyToolsItem
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedBackTopAppBar
import com.dokiwei.zshg.tool.ui.component.MyEditableDropdownMenu
import com.dokiwei.zshg.tool.ui.component.SpiderWebRadarLineDiagram

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PotentialCalculationScreen(BackToTools: () -> Unit){

    Scaffold(
        topBar = {
            MyCenterAlignedBackTopAppBar(MyToolsItem.PotentialCalculation.title,BackToTools)
        },
        content = { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                //人物图以及六维图
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    //人物图
                    Box(Modifier.weight(1f)) {
                        Image(
                            painterResource(R.drawable.ic_test),
                            null,
                            Modifier
                                .size(200.dp)
                                .padding(30.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    //六维图
                    Box(Modifier.weight(1f)){
                        SpiderWebRadarLineDiagram(
                            modifier = Modifier.size(200.dp),
                            dataList = listOf(1.3f, 0.2f, 1.2f, 0.8f, 0.53f, 0.64f),
                            labelList = listOf("感知", "意志", "敏捷", "技巧", "力量","体质")
                        )
                    }
                }
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(180.dp)) {
                    Text(
                        text = "职业",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        val items = listOf("新兵", "弓箭手", "轻步兵", "轻骑士", "巫师")
                        MyEditableDropdownMenu ("预览",items,"新兵")
                    }
                }
            }
        }
    )
}




@Preview
@Composable
fun PreviewPotentialCalculationScreen(){
    PotentialCalculationScreen{}
}