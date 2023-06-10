package com.dokiwei.zshg.tool.ui.component

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyEditableDropdownMenu(label: String, items : List<String>, selectedText : String) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItemText by remember { mutableStateOf(selectedText) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            // 为了正确起见，必须将“menuAnchor”修饰符传递给文本字段。
            modifier = Modifier.menuAnchor(),
            value = selectedItemText,
            onValueChange = { selectedItemText = it },
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        // 基于文本字段值的筛选器选项
        val filteringOptions = items.filter { it.contains(selectedItemText, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedItemText = selectionOption
                            expanded = false
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
fun Preview(){
    val items = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    MyEditableDropdownMenu ("预览",items,"Option 1")
}
