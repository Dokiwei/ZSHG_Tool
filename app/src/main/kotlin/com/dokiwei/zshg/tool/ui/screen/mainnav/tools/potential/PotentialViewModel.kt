package com.dokiwei.zshg.tool.ui.screen.mainnav.tools.potential

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.dokiwei.zshg.tool.data.Role
import kotlinx.coroutines.flow.MutableStateFlow

class PotentialViewModel : ViewModel() {

    private val _lastCharactersInSixDimensions = MutableStateFlow(
        listOf(
            0f,
            0f,
            0f,
            0f,
            0f,
            0f
        )
    )
    val lastCharactersInSixDimensions = _lastCharactersInSixDimensions

    private val _charactersInSixDimensions = SnapshotStateList<List<Float>>()
    val charactersInSixDimensions = _charactersInSixDimensions
    fun updateCharactersInSixDimensions(index: Int, role: Role) {
        _charactersInSixDimensions.apply {
            this[index] = listOf(
                role.grow.str.toFloat(),
                role.grow.phy.toFloat(),
                role.grow.per.toFloat(),
                role.grow.wil.toFloat(),
                role.grow.agi.toFloat(),
                role.grow.dex.toFloat()
            )
        }
        _lastCharactersInSixDimensions.apply {
            value=_charactersInSixDimensions[index]
        }
    }
    fun addCharactersInSixDimensions(){
        _charactersInSixDimensions.apply {
            this.add(listOf(0f,0f,0f,0f,0f,0f))
        }
    }
    fun removeLastCharactersInSixDimensions(){
        _charactersInSixDimensions.apply {
            this.removeLastOrNull()
        }
    }

    private val _selectedTexts = SnapshotStateList<String>()
    val selectedTexts = _selectedTexts
    fun addSelectedTexts() {
        _selectedTexts.apply {
            Log.e("添加文字", "${this.add("")}")
        }
    }

    fun removeLastSelectedTexts() {
        _selectedTexts.apply {
            this.removeLastOrNull()?.let { Log.e("删除文字", it) }
        }
    }

    fun updateSelectedTexts(index: Int, string: String) {
        _selectedTexts.apply {
            Log.e("viewModel更新文字", "${index}更新前:${this[index]} 更新位置及数据:$index $string")
            this[index] = string
            Log.e("viewModel更新文字", "${index}更新后:${this[index]}")
        }
    }

    private val _expands = SnapshotStateList<Boolean>()
    val expands = _expands
    fun addExpand() {
        _expands.apply {
            Log.e("viewModel添加expand", "是否成功:${this.add(false)}")
        }
    }

    fun removeLastExpand() {
        _expands.apply {
            this.removeLastOrNull()?.let { Log.e("viewModel删除最后一个expand", "值:$it") }
        }
    }

    fun updateExpand(index: Int, boolean: Boolean) {
        _expands.apply {
            this[index] = boolean
            Log.e("viewModel更新expand", "${index}更新后:${this[index]}")
        }
    }
}