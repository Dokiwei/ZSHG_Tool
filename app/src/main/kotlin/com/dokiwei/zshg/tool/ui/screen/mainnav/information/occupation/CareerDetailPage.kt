package com.dokiwei.zshg.tool.ui.screen.mainnav.information.occupation

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.dokiwei.zshg.tool.data.Grow
import com.dokiwei.zshg.tool.data.Role
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedBackTopAppBar
import com.dokiwei.zshg.tool.ui.component.SpiderWebRadarLineDiagram

/**
 * 职业详细页
 * @author DokiWei
 * @date 2023/6/15 17:36
 *
 * @param role 页面所要展示的职业信息[Role]类
 * @param back 页面返回的目标页,传入navController.navigate()方法
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareerDetailPage(role: Role, back: () -> Unit) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = "http://101.201.46.104:8084/image/兵种/${role.profession}/${role.name}.gif")
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(1000)
            }).build(),
        ImageLoader.Builder(LocalContext.current)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    )
    //图片加载状态
    when (painter.state) {
        is AsyncImagePainter.State.Success -> {
            Log.i("Coil", "角色图片加载完成")
        }

        is AsyncImagePainter.State.Loading -> {
            Log.i("Coil", "角色图片加载中....")
        }

        is AsyncImagePainter.State.Error -> {
            Log.i("Coil", "角色图片加载错误")
        }

        else -> {}
    }
    Scaffold(
        topBar = {
            MyCenterAlignedBackTopAppBar(role.name, back)
        }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            //顶部图片和六维
            Head(role, painter)
            LazyColumn {
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
                        Text(text = role.armor, Modifier.padding(top = 8.dp))
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
                        Text(text = role.weapons, Modifier.padding(top = 8.dp))
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
                        ) {
                            Text(
                                text = "力量:${role.grow.str}",
                                modifier = Modifier.fillMaxWidth(0.5f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "体质:${role.grow.phy}",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                        ) {
                            Text(
                                text = "技巧:${role.grow.dex}",
                                modifier = Modifier.fillMaxWidth(0.5f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "感知:${role.grow.per}",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                        ) {
                            Text(
                                text = "敏捷:${role.grow.agi}",
                                modifier = Modifier.fillMaxWidth(0.5f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "意志:${role.grow.wil}",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Head(role: Role, painter: AsyncImagePainter) {
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
                    role.grow.str.toFloat(),
                    role.grow.phy.toFloat(),
                    role.grow.per.toFloat(),
                    role.grow.wil.toFloat(),
                    role.grow.agi.toFloat(),
                    role.grow.dex.toFloat()
                ),
                lineRadius = 8f
            )
        }
    }
    ListItem(
        headlineText = {
            Row {
                role.profession.takeIf { s -> s != "新兵" }?.let { ss ->
                    AsyncImage(
                        model = "http://101.201.46.104:8084/image/精通/${ss}.png",
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = role.profession,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        overlineText = { Text(text = digitalTransformation(role.level)) }
    )
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
            Grow(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
            )
        )
    ) {}
}