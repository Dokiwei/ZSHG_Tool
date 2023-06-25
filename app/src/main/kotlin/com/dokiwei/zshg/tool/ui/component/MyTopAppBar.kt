package com.dokiwei.zshg.tool.ui.component

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dokiwei.zshg.tool.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCenterAlignedTopAppBar(title : String){
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCenterAlignedBackTopAppBar(title : String,onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    "返回",
                    Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

fun Back() {
    Log.i("TopBar","Back")
}

@Preview
@Composable
fun PreviewTopBar(){
    MyCenterAlignedTopAppBar("测试")
}

@Preview
@Composable
fun PreviewBackTopBar(){
    MyCenterAlignedBackTopAppBar("测试") { Back() }
}