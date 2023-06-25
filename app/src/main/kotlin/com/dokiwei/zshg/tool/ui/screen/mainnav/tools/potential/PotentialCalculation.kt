package com.dokiwei.zshg.tool.ui.screen.mainnav.tools.potential

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dokiwei.zshg.tool.R
import com.dokiwei.zshg.tool.data.MyRoute
import com.dokiwei.zshg.tool.data.Role
import com.dokiwei.zshg.tool.data.loadRoles
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedBackTopAppBar
import com.dokiwei.zshg.tool.ui.component.SpiderWebRadarLineDiagram

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PotentialCalculationScreen(backToTools: () -> Unit) {
    val viewModel: PotentialViewModel = viewModel()
    val charactersInSixDimensions = viewModel.charactersInSixDimensions
    val lastCharactersInSixDimensions by viewModel.lastCharactersInSixDimensions.collectAsState()
    val roles = loadRoles(LocalContext.current)
    val items = mutableListOf<String>()
    roles.forEach { role ->
        role.name.let { items.add(it) }
    }
    val selectedTexts = viewModel.selectedTexts
    val expands = viewModel.expands
    Scaffold(
        topBar = {
            MyCenterAlignedBackTopAppBar(MyRoute.ToolRoute.POTENTIAL_CALCULATION, backToTools)
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            //人物图以及六维图
            HeadBox(
                lastCharactersInSixDimensions
            )
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        CareerOptions(selectedTexts, expands, viewModel, items, roles)
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                viewModel.addSelectedTexts()
                                viewModel.addExpand()
                                viewModel.addCharactersInSixDimensions()
                            }
                            ) {
                                Text("添加职业")
                            }
                            Button(onClick = {
                                viewModel.removeLastSelectedTexts()
                                viewModel.removeLastExpand()
                                viewModel.removeLastCharactersInSixDimensions()
                            }) {
                                Text("删除职业")
                            }
                            Button(onClick = {
                                expands.forEach {
                                    Log.e("输出expand", "$it")
                                }
                                selectedTexts.forEach {
                                    Log.e("输出selectedText", it)
                                }
                                charactersInSixDimensions.forEach {
                                    Log.e("输出charactersInSixDimensions", "$it")
                                }
                                Log.e(
                                    "输出lastCharactersInSixDimensions",
                                    "$lastCharactersInSixDimensions"
                                )
                            }) {
                                Text("输出信息")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CareerOptions(
    selectedTexts: SnapshotStateList<String>,
    expands: SnapshotStateList<Boolean>,
    viewModel: PotentialViewModel,
    items: MutableList<String>,
    roles: MutableList<Role>
) {
    LazyColumn {
        items(selectedTexts.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                ExposedDropdownMenuBox(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expands[index],
                    onExpandedChange = {
                        viewModel.updateExpand(index, it)
                    },
                ) {
                    OutlinedTextField(
                        // 为了正确起见，必须将“menuAnchor”修饰符传递给文本字段。
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        value = selectedTexts[index],
                        onValueChange = {
                            viewModel.updateSelectedTexts(
                                index,
                                it
                            )
                        },
                        label = { Text("职业") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expands[index]
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    )
                    // 基于文本字段值的筛选器选项
                    val filteringOptions =
                        items.filter { it.contains(selectedTexts[index]) }
                    if (filteringOptions.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = expands[index],
                            onDismissRequest = {
                                viewModel.updateExpand(index, false)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items.forEachIndexed { index1, s ->
                                DropdownMenuItem(
                                    text = { Text(s) },
                                    onClick = {
                                        viewModel.updateCharactersInSixDimensions(
                                            index,
                                            roles[index1]
                                        )
                                        viewModel.updateSelectedTexts(index, s)
                                        viewModel.updateExpand(index, false)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeadBox(charactersInSixDimensions: List<Float>) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        //人物图
        Box(
            Modifier.weight(1f)
        ) {
            Image(
                painterResource(R.drawable.img_test),
                null,
                Modifier
                    .size(200.dp)
                    .padding(30.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
        }
        //六维图
        Box(Modifier.weight(1f)) {
            SpiderWebRadarLineDiagram(
                modifier = Modifier.size(200.dp),
                dataList = charactersInSixDimensions,
                lineRadius = 8f
            )
        }
    }
}

@Preview
@Composable
fun PreviewPotentialCalculationScreen() {
    PotentialCalculationScreen {}
}