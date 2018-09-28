package cn.wycode.dota2crawler

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor


class PropListProcessor : PageProcessor {

    private val site: Site = Site.me().setSleepTime((Math.random() * 1000 + 1000).toInt())

    private lateinit var itemObjects: JSONObject

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        if (page != null) {
            if (page.url.get() == "http://www.dota2.com/items/json") {
                val shopColumns = page.html.xpath("//div[@class='shopColumn']").nodes()
                for (i in 0 until shopColumns.size) {
                    val shopColumn = shopColumns[i]
                    val type = shopColumn.xpath("//img[@class='shopColumnHeaderImg']/@title").get().trim()
                    var className = when {
                        i < 4 -> "基础物品"
                        i < 11 -> "升级物品"
                        i == 11 -> "神秘商店"
                        else -> throw Exception("结构变更！")
                    }
                    println("处理：$type")
                    val itemDivs = shopColumn.xpath("//div[@class='floatItemImage itemIconWithTooltip']").nodes()
                    val items = ArrayList<Item>()
                    for (div in itemDivs){
                        val key = div.xpath("//div[@class='floatItemImage itemIconWithTooltip']/@itemname").get()
                        val itemObject = itemObjects.getJSONObject(key)
                        val id = itemObject.getIntValue("id")
                        val name = itemObject.getString("dname")

                    }

                }
            } else if (page.url.get() == "http://www.dota2.com/jsfeed/heropediadata?feeds=itemdata&v=47284738sc2aWpANpKA&l=schinese") {
                println(page.json.get())
                itemObjects = JSON.parseObject(page.json.get()).getJSONObject("itemdata")
            }
        }
    }

}

data class Item(
        val id: Int = 0
)