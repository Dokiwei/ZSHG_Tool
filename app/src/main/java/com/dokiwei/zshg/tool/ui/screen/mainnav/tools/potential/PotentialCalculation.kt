package com.dokiwei.zshg.tool.ui.screen.mainnav.tools.potential

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
    val charactersInSixDimensions: List<Float> by viewModel.charactersInSixDimensions.collectAsState()
    val roles = loadRoles(LocalContext.current)
    val items = mutableListOf<String>()
    roles.forEach { role ->
        role.name.let { items.add(it) }
    }
    val expanded by viewModel.expanded.collectAsState()
    val selectedItemText by viewModel.selectedItemText.collectAsState()
    val checked by viewModel.checkedEdit.collectAsState()
    Scaffold(
        topBar = {
            MyCenterAlignedBackTopAppBar(MyRoute.ToolRoute.POTENTIAL_CALCULATION, backToTools)
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            //人物图以及六维图
            HeadBox(charactersInSixDimensions)

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
                    ListOfOccupations(items, checked, viewModel, expanded, selectedItemText, roles)
                    Column {

                    }
                }
            }
        }
    }
}

@Composable
private fun ListOfOccupations(
    items: MutableList<String>,
    checked: Boolean,
    viewModel: PotentialViewModel,
    expanded: Boolean,
    selectedItemText: String,
    roles: MutableList<Role>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MyEditableDropdownMenu(
            label = "职业",
            items = items,
            readOnly = !checked,
            viewModel = viewModel,
            expanded = expanded,
            selectedItemText = selectedItemText
        ) { index ->
            viewModel.updateCharactersInSixDimensions(
                listOf(
                    roles[index].str.toFloat(),
                    roles[index].phy.toFloat(),
                    roles[index].per.toFloat(),
                    roles[index].wil.toFloat(),
                    roles[index].agi.toFloat(),
                    roles[index].dex.toFloat()
                )
            )
        }
        // 图标不可聚焦，无需内容描述
        val icon: (@Composable () -> Unit)? = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            null
        }
        Switch(
            modifier = Modifier.semantics { contentDescription = "输入开关" },
            checked = checked,
            onCheckedChange = { viewModel.updateChecked() },
            thumbContent = icon
        )
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
        Box(Modifier.weight(1f)) {
            SpiderWebRadarLineDiagram(
                modifier = Modifier.size(200.dp),
                dataList = charactersInSixDimensions,
                lineRadius = 8f
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyEditableDropdownMenu(
    label: String,
    items: List<String>,
    readOnly: Boolean = true,
    viewModel: PotentialViewModel,
    expanded: Boolean,
    selectedItemText: String,
    onItemSelected: (Int) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { viewModel.updateExpanded() },
    ) {
        TextField(
            // 为了正确起见，必须将“menuAnchor”修饰符传递给文本字段。
            modifier = Modifier.menuAnchor(),
            readOnly = readOnly,
            value = selectedItemText,
            onValueChange = { viewModel.updateSelectedItemText(it) },
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        // 基于文本字段值的筛选器选项
        val filteringOptions = items.filter { it.contains(selectedItemText) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { viewModel.updateExpanded(false) },
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(s) },
                        onClick = {
                            onItemSelected(index)
                            viewModel.updateSelectedItemText(s)
                            viewModel.updateExpanded(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPotentialCalculationScreen() {
    PotentialCalculationScreen {}
}