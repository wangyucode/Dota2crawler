package cn.wycode.dota2crawler.azhang

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor


class HeroAzhangProcessor : PageProcessor {

    private val site: Site = Site.me().setSleepTime((Math.random() * 500 + 1000).toInt())

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        if (page != null) {
            //info
            val content = page.html.xpath("//div[@id='mw-content-text']")
            val heroTable = content.xpath("//div[@class='mw-parser-output']/table/tbody/tr/")
            val heros = heroTable.xpath("//li").nodes()
            val names = ArrayList<String>(heros.size)
            for(hero in heros){
                val name = hero.xpath("//a[2]/text()").get().trim()
                names.add(name)
            }
            page.putField("names",names)
        }
    }
}
