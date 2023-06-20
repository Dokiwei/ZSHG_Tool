package com.dokiwei.zshg.tool.ui.screen.mainnav.information.occupation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.dokiwei.zshg.tool.data.Role
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedBackTopAppBar
import com.dokiwei.zshg.tool.ui.component.SpiderWebRadarLineDiagram

/**
 * @author DokiWei
 * @date 2023/6/15 17:36
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun CareerDetailPage(role: Role, backToOccupation: () -> Unit) {
    val painter = rememberImagePainter(
        data = "http://pic-bucket.ws.126.net/photo/0003/2021-11-16/GOTKEOOU00AJ0003NOS.jpg",
        builder = {
            crossfade(1000)
        }
    )
    //图片加载状态
    when (painter.state) {
        is ImagePainter.State.Success -> {
            Log.i("Coil", "图片加载完成")
        }

        is ImagePainter.State.Loading -> {
            Log.i("Coil", "图片加载中....")
        }

        is ImagePainter.State.Error -> {
            Log.i("Coil", "图片加载错误")
        }

        else -> {}
    }
    Scaffold(
        topBar = {
            MyCenterAlignedBackTopAppBar(role.name, backToOccupation)
        }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            //顶部图片和六维
            Head(role, painter)
            ListItem(
                headlineText = {
                    Text(
                        text = role.profession,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                overlineText = { Text(text = digitalTransformation(role.level)) }
            )
            LazyColumn{
                item {
                    Text(text = role.desc, Modifier.padding(16.dp, 8.dp))
                    Divider()
                    //护甲类型
                    Column(modifier = Modifier.padding(16.dp, 8.dp)) {
                        Text(
                            text = "护甲类型",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(text = role.armor,Modifier.padding(top = 8.dp))
                    }
                    Divider()
                    //可用武器
                    Column(modifier = Modifier.padding(16.dp, 8.dp)) {
                        Text(
                            text = "可用武器",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(text = role.weapons,Modifier.padding(top = 8.dp))
                    }
                    Divider()
                    //职业成长
                    Column(modifier = Modifier.padding(16.dp, 8.dp)) {
                        Text(
                            text = "职业成长",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(
                            Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "力量:${role.str}")
                            Text(text = "体质:${role.phy}")
                        }
                        Row(
                            Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "技巧:${role.dex}")
                            Text(text = "感知:${role.per}")
                        }
                        Row(
                            Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "敏捷:${role.agi}")
                            Text(text = "意志:${role.wil}")
                        }
                    }
                }
            }
        }
    }
}

private fun digitalTransformation(num: Int): String {
    var s = ""
    when (num) {
        0 -> s = "默认"
        1 -> s = "一转"
        2 -> s = "二转"
        3 -> s = "三转"
    }
    return s
}

@Composable
private fun Head(role: Role, painter: ImagePainter) {
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            SpiderWebRadarLineDiagram(
                modifier = Modifier.fillMaxSize(),
                dataList = listOf(
                    role.str.toFloat(),
                    role.phy.toFloat(),
                    role.per.toFloat(),
                    role.wil.toFloat(),
                    role.agi.toFloat(),
                    role.dex.toFloat()
                ),
                lineRadius = 8f
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CareerDetailPage(
        Role(
            "演示", "演示", 0,
            "简介",
            "护甲",
            "武器",
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0
        )
    ) {

    }
}