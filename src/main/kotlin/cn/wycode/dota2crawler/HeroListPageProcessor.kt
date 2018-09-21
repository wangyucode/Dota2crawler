package cn.wycode.dota2crawler

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor


class HeroListPageProcessor : PageProcessor {

    private val site: Site = Site.me().setSleepTime((Math.random() * 1000 + 1000).toInt())
    var type: String = "力量"

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        if (page != null) {
            val name = page.html.xpath("//h1[@class='firstHeading']/text()").get()
            if ("英雄" == name) {
                val table = page.html.css("table")
                val links = table.links().all()
                println(this)
                page.addTargetRequests(links)
            } else if ("力量" == name || "智力" == name || "敏捷" == name) {
                type = name
            } else {
                println(this)
                page.putField("name", name)
                page.putField("type", type)
                val infoBox = page.html.xpath("//table[@class='infobox']")
                val imageUrl = infoBox.xpath("//tbody/tr[2]/td/a/img/@src").get()
                page.putField("imageUrl", imageUrl)
            }
        }
    }

}