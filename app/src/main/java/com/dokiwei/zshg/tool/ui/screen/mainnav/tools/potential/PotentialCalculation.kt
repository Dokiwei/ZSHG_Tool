package com.dokiwei.zshg.tool.ui.screen.mainnav.tools.potential

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dokiwei.zshg.tool.R
import com.dokiwei.zshg.tool.data.MyRoute
import com.dokiwei.zshg.tool.data.Role
import com.dokiwei.zshg.tool.ui.component.MyCenterAlignedBackTopAppBar
import com.dokiwei.zshg.tool.ui.component.MyEditableDropdownMenu
import com.dokiwei.zshg.tool.ui.component.SpiderWebRadarLineDiagram
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.OutputStreamWriter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PotentialCalculationScreen(backToTools: () -> Unit) {

    var charactersInSixDimensions by remember {
        mutableStateOf(
            listOf(
                0.2f,
                0.4f,
                0.6f,
                0.8f,
                1.0f,
                1.2f
            )
        )
    }

    Scaffold(
        topBar = {
            MyCenterAlignedBackTopAppBar(MyRoute.ToolRoute.POTENTIAL_CALCULATION, backToTools)
        }
    ) { innerPadding ->
        val context = LocalContext.current
        Column(Modifier.padding(innerPadding)) {
            //人物图以及六维图
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                //人物图
                Box(
                    Modifier
                        .weight(1f)
                        .clickable {
                            charactersInSixDimensions = listOf(1f, 1f, 1f, 1f, 1f, 1f)
                        }) {
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
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "职业",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val roles = loadRoles(context = context)
                    val items = mutableListOf<String>()
                    roles.forEach { role ->
                        items.add(role.name)
                    }
                    MyEditableDropdownMenu("职业", items, items[0]) { index ->
                        charactersInSixDimensions = listOf(
                            roles[index].str.toFloat(),
                            roles[index].phy.toFloat(),
                            roles[index].per.toFloat(),
                            roles[index].wil.toFloat(),
                            roles[index].agi.toFloat(),
                            roles[index].dex.toFloat()
                        )
                        Log.i("测试", "" + index + roles[index].name + charactersInSixDimensions)
                    }
//                    var expanded by remember { mutableStateOf(false) }
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        items.forEachIndexed { index, s ->
//                            DropdownMenuItem(
//                                text = { Text(s) },
//                                onClick = {
//                                    charactersInSixDimensions = setValue(
//                                        listOf(
//                                            roles[index].str.toFloat(),
//                                            roles[index].dex.toFloat(),
//                                            roles[index].agi.toFloat(),
//                                            roles[index].phy.toFloat(),
//                                            roles[index].per.toFloat(),
//                                            roles[index].wil.toFloat()
//                                        )
//                                    )
//                                    expanded = false
//                                })
//                        }
//                    }
                }
            }
        }
    }
}

fun loadRoles(context: Context): MutableList<Role> {
    val roles = mutableListOf<Role>()
    val jsonString = context.assets.open("roles.json").bufferedReader().use { it.readText() }
    val jsonArray = JSONArray(jsonString)
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        roles.add(
            Role(
                jsonObject.getString("name"),
                jsonObject.getString("profession"),
                jsonObject.getInt("level"),
                jsonObject.getDouble("str"),
                jsonObject.getDouble("dex"),
                jsonObject.getDouble("agi"),
                jsonObject.getDouble("phy"),
                jsonObject.getDouble("per"),
                jsonObject.getDouble("wil")
            )
        )
    }
    return roles
}

fun saveRoles(context: Context, roles: MutableList<Role>) {
    /*    val roles = mutableListOf<Role>()
        roles.add(Role("新兵","新兵",0,0.123,0.123,0.123,0.123,0.0,0.0))
        roles.add(Role("见习骑兵","骑兵",1,0.23,0.15,0.105,0.165,0.0,0.0))
        roles.add(Role("猎人","弓兵",1,0.11,0.23,0.20,0.165,0.0,0.0))
        saveRoles(context,roles)*/
    val jsonArray = JSONArray()
    roles.forEach { role ->
        jsonArray.put(
            JSONObject(
                mapOf(
                    "name" to role.name,
                    "profession" to role.profession,
                    "level" to role.level,
                    "str" to role.str,
                    "dex" to role.dex,
                    "agi" to role.agi,
                    "phy" to role.phy,
                    "per" to role.per,
                    "wil" to role.wil
                )
            )
        )
    }
    val output = context.openFileOutput("roles.json", Context.MODE_PRIVATE)
    val writer = BufferedWriter(OutputStreamWriter(output))
    writer.write(jsonArray.toString())
    writer.close()
}

@Preview
@Composable
fun PreviewPotentialCalculationScreen() {
    PotentialCalculationScreen {}
}