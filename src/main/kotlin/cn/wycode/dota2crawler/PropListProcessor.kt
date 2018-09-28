package cn.wycode.dota2crawler

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.apache.commons.lang3.StringUtils
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor
import us.codecraft.webmagic.selector.Html


class PropListProcessor : PageProcessor {

    private val site: Site = Site.me().setSleepTime((Math.random() * 1000 + 1000).toInt()).addCookie("Steam_Language", "schinese")

    private lateinit var itemObjects: JSONObject

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        if (page != null) {
            if (page.url.get() == "http://www.dota2.com/items/") {
                val shopColumns = page.html.xpath("//div[@class='shopColumn']").nodes()
                val items = ArrayList<Item>()
                for (i in 0 until shopColumns.size) {
                    val shopColumn = shopColumns[i]
                    val type = shopColumn.xpath("//img[@class='shopColumnHeaderImg']/@title").get().trim()
                    val className = when {
                        i < 4 -> "基础物品"
                        i < 11 -> "升级物品"
                        i == 11 -> "神秘商店"
                        else -> throw Exception("结构变更！")
                    }
                    println("处理：$type")
                    val itemDivs = shopColumn.xpath("//div[@class='floatItemImage itemIconWithTooltip']").nodes()

                    for (div in itemDivs) {
                        val key = div.xpath("//div[@class='floatItemImage itemIconWithTooltip']/@itemname").get()
                        val itemObject = itemObjects.getJSONObject(key)
                        val name = itemObject.getString("dname")
                        val lore = itemObject.getString("lore")
                        val img = "http://cdn.dota2.com/apps/dota2/images/items/" + itemObject.getString("img")
                        var notes = itemObject.getString("notes")
                                .replace("<br />", "\n")
                        var descText = itemObject.getString("desc")
                                .replace('\r', 'a')
                                .replace('\n', 'a')
                        if (key.startsWith("necronomicon")) {
                            descText = descText.replace("<br><br>", "<br />aa")
                        }
                        val descEntries =descText.split("<br />aa")
                        val desc = HashMap<String, String>(descEntries.size)
                        for (descEntry in descEntries) {
                            if (descEntry.isNotEmpty()) {
                                when {
                                    descEntry.contains("</h1>") -> {
                                        val descEntryArray = descEntry.split("</h1>")
                                        val descKey = descEntryArray[0].replace("<h1>", "").trim()
                                        val value = descEntryArray[1]
                                                .replace("<br><br>", "\n", ignoreCase = true)
                                                .replace("<br>", "\n", ignoreCase = true)
                                                .replace("<font color='#e03e2e'>", "")
                                                .replace("</font>", "")
                                                .replace("<span class=\"GameplayValues GameplayVariable\">", "")
                                                .replace("%tooltip_mana_per_charge%", "1")
                                                .replace("%bonus_health_health%", "13")
                                                .replace("己方已使用本数：%customval_team_tomes_used%", "知识之书有3个存货限制，每10分钟才会补充1本")
                                                .replace("</span>", "")
                                                .trim()
                                        desc[descKey] = value
                                    }
                                    notes.isNotEmpty() -> notes = notes + "\n" + descEntry
                                    else -> notes = descEntry
                                }
                            }
                        }
                        val cost = itemObject.getIntValue("cost")
                        val mc = itemObject.getString("mc").replace("false", "0")
                        val cd = itemObject.getIntValue("cd")
                        val components = (itemObject.getJSONArray("components") ?: ArrayList<String>()) as List<String>
                        val attribString = itemObject.getString("attrib")
                                .replace('+', ' ')
                                .replace('\n', ' ')
                                .trim()
                        val attribKeys = Html(attribString).xpath("//span[@class='attribValText']/text()").all()
                        val attribValues = Html(attribString).xpath("//span[@class='attribVal']/text()").all()
                        val attrs = HashMap<String, String>(attribKeys.size)
                        for (j in 0 until attribKeys.size) {
                            attrs[attribKeys[j]] = attribValues[j]
                        }
                        val item = Item(key, type, className, name, lore, img, notes, desc, cost, mc, cd, components, attrs)
                        println(item.toString())
                        items.add(item)
                    }

                }
                page.putField("items", items)
            } else if (page.url.get() == "http://www.dota2.com/jsfeed/heropediadata?feeds=itemdata&v=47284738sc2aWpANpKA&l=schinese") {
                println(page.json.get())
                itemObjects = JSON.parseObject(page.json.get()).getJSONObject("itemdata")
            }
        }
    }

}

data class Item(
        val key: String = "",
        val type: String = "",
        val cname: String = "",
        val name: String = "",
        val lore: String = "",
        val img: String = "",
        val notes: String? = "",
        val desc: Map<String, String> = HashMap(),
        val cost: Int = 0,
        val mc: String? = "",
        val cd: Int = 0,
        val components: List<String> = ArrayList(),
        val attrs: Map<String, String> = HashMap()
)