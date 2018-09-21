package cn.wycode.dota2crawler

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor

class HeroListPageProcessor : PageProcessor {

    private val site: Site = Site.me().setSleepTime((Math.random() * 1000 + 1000).toInt())

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        if (page != null) {
            val name = page.html.xpath("//h1[@class='firstHeading']/text()").get()
            if("英雄" == name){
                val table = page.html.css("table")
                val links = table.links().all().filter {
                    !it.endsWith("%e6%95%8f%e6%8d%b7", true) && //敏捷
                            !it.endsWith("%e5%8a%9b%e9%87%8f", true) && //力量
                            !it.endsWith("%e6%99%ba%e5%8a%9b", true) //智力
                }
                println(links.size)
                page.addTargetRequests(links)
            }else{
                page.putField("name",name)
            }

        }
    }
}