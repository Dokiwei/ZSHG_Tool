package com.dokiwei.zshg.tool.ui.screen.mainnav.information.occupation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dokiwei.zshg.tool.data.Hero
import com.dokiwei.zshg.tool.data.MyRoute
import com.dokiwei.zshg.tool.data.loadRoles
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedBackTopAppBar
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OccupationScreen(navController: NavHostController, back: () -> Unit) {
    val viewModel: OccupationViewModel = viewModel()
    val roles = loadRoles(LocalContext.current)
    val items = mutableListOf<Hero>()
    roles.forEachIndexed { index, role ->
        items.add(Hero(role.name, role.profession, index))
    }
    Scaffold(
        topBar = {
            MyCenterAlignedBackTopAppBar(MyRoute.InformationRoute.OCCUPATION, back)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Card(
                Modifier
                    .semantics {
                        @Suppress("DEPRECATION")
                        isContainer = true
                    }
                    .zIndex(1f)
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                SearchBar(
                    viewModel,
                    items,
                    click = { index ->
                        val role = Gson().toJson(roles[index])
                        navController.navigate("职业详细页/$role")
                    }
                ) { back() }
            }
            LazyColumn {
                items.forEach {
                    item {
                        ListItem(
                            headlineText = {
                                Text(
                                    text = it.name
                                )
                            },
                            supportingText = {
                                Text(
                                    text = it.profession
                                )
                            },
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        val role = Gson().toJson(roles[it.index])
                                        navController.navigate("职业详细页/$role")
                                    })
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: OccupationViewModel,
    items: MutableList<Hero>,
    click: (index: Int) -> Unit,
    back: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var expand by remember { mutableStateOf(true) }
    val searchText by viewModel.searchText.collectAsState()
    val filteredList by viewModel.filteredList.collectAsState()
    viewModel.updateFilteredList(items)
    Column {
        TextField(
            placeholder = { Text("请输入职业名字或类型...") },
            value = searchText,
            onValueChange = { viewModel.updateSearchText(it) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { expand = it.isFocused },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "搜索图标"
                )
            },
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomStart = if (expand) 0.dp else 24.dp,
                bottomEnd = if (expand) 0.dp else 24.dp,
            ),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        AnimatedVisibility(
            visible = expand,
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }
        ) {
            BoxWithConstraints {
                if (expand) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = if (maxHeight > 280.dp) 280.dp else maxHeight)
                            .padding(top = 10.dp, bottom = 10.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                    ) {
                        filteredList.forEach {
                            item {
                                ListItem(
                                    headlineText = {
                                        Text(
                                            text = it.name
                                        )
                                    },
                                    supportingText = {
                                        Text(
                                            text = it.profession
                                        )
                                    },
                                    modifier = Modifier
                                        .clickable {
                                            click(it.index)
                                        },
                                    colors = ListItemDefaults.colors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        BackHandler(onBack = {
            if (expand) {
                expand = false
                focusManager.clearFocus()
            } else {
                back()
            }
        })
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
}