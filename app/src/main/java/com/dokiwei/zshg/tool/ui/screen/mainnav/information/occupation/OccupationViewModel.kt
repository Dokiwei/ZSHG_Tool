package com.dokiwei.zshg.tool.ui.screen.mainnav.information.occupation

import androidx.lifecycle.ViewModel
import com.dokiwei.zshg.tool.data.Hero
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author DokiWei
 * @date 2023/6/15 17:10
 */
class OccupationViewModel : ViewModel() {
    private val list: List<Hero> = listOf(Hero("", "", 0))
    private val _filteredList = MutableStateFlow(list)
    val filteredList = _filteredList
    fun updateFilteredList(items: MutableList<Hero>) {
        _filteredList.apply {
            value = items.filter {
                it.name.contains(_searchText.value) || it.profession.contains(_searchText.value)
            }
        }
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText
    fun updateSearchText(searchText: String) {
        _searchText.apply {
            value = searchText
        }
    }
}