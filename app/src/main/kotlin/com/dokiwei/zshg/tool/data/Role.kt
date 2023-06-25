package com.dokiwei.zshg.tool.data

import android.content.Context
import org.json.JSONArray

/*    val str: Double,
    val dex: Double,
    val agi: Double,
    val phy: Double,
    val per: Double,
    val wil: Double,*/
data class Role(
    val name: String,
    val profession: String,
    val level: Int,
    val desc: String,
    val armor: String,
    val weapons: String,
    val grow : Grow,

)
data class Grow(
    val str: Double,
    val dex: Double,
    val agi: Double,
    val phy: Double,
    val per: Double,
    val wil: Double
)
/*                str = jsonObject.getDouble("str"),
                dex = jsonObject.getDouble("dex"),
                agi = jsonObject.getDouble("agi"),
                phy = jsonObject.getDouble("phy"),
                per = jsonObject.getDouble("per"),
                wil = jsonObject.getDouble("wil")*/
fun loadRoles(context: Context): MutableList<Role> {
    val roles = mutableListOf<Role>()
    val jsonString = context.assets.open("roles.json").bufferedReader().use { it.readText() }
    val jsonArray = JSONArray(jsonString)
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        roles.add(
            Role(
                name = jsonObject.getString("name"),
                profession = jsonObject.getString("profession"),
                level = jsonObject.getInt("level"),
                desc = jsonObject.getString("description"),
                armor = jsonObject.getString("armor"),
                weapons = jsonObject.getString("weapons"),
                grow = Grow(
                    str = jsonObject.getJSONObject("grow").getDouble("str"),
                    dex = jsonObject.getJSONObject("grow").getDouble("dex"),
                    agi = jsonObject.getJSONObject("grow").getDouble("agi"),
                    phy = jsonObject.getJSONObject("grow").getDouble("phy"),
                    per = jsonObject.getJSONObject("grow").getDouble("per"),
                    wil = jsonObject.getJSONObject("grow").getDouble("wil")
                )
            )
        )
    }
    return roles
}

/*
fun saveRoles(context: Context, roles: MutableList<Role>) {

    val roles = mutableListOf<Role>()
        roles.add(Role("新兵","新兵",0,0.123,0.123,0.123,0.123,0.0,0.0))
        roles.add(Role("见习骑兵","骑兵",1,0.23,0.15,0.105,0.165,0.0,0.0))
        roles.add(Role("猎人","弓兵",1,0.11,0.23,0.20,0.165,0.0,0.0))
        saveRoles(context,roles)

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
*/


