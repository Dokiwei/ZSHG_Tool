package com.dokiwei.zshg.tool.ui.screen.mainnav.tools.potential

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class PotentialViewModel : ViewModel() {
    private val _charactersInSixDimensions = MutableStateFlow(
        listOf(
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f,
            0.0f
        )
    )
    val charactersInSixDimensions = _charactersInSixDimensions
    fun updateCharactersInSixDimensions(values: List<Float>) {
        _charactersInSixDimensions.apply { value = values }
    }


    private val _checkedEdit = MutableStateFlow(false)
    val checkedEdit = _checkedEdit
    fun updateChecked() {
        _checkedEdit.apply { value = !value }
    }

    private val _expanded = MutableStateFlow(false)
    val expanded = _expanded
    fun updateExpanded() {
        _expanded.apply { value = !value }
    }
    fun updateExpanded(it:Boolean) {
        _expanded.apply { value = it }
    }
    private val _selectedItemText = MutableStateFlow("")
    val selectedItemText = _selectedItemText
    fun updateSelectedItemText(it: String) {
        _selectedItemText.apply { value = it }
    }
}