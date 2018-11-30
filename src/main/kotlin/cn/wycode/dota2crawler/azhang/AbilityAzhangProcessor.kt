package cn.wycode.dota2crawler.azhang

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor
import us.codecraft.webmagic.selector.Html


class AbilityAzhangProcessor : PageProcessor {

    private val site: Site = Site.me().setSleepTime((Math.random() * 500 + 1000).toInt())

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        if (page != null) {
            //info
            val content = page.html.xpath("//div[@class='mw-collapsible-content']").all()
            val upgradeHtml = Html(content[0])
            val heroNames = upgradeHtml.xpath("//div[@class='skilllist-rich-head']/a[1]/text()").all()
            val upgradeSkills = ArrayList<AZhangSkill>(heroNames.size)
            for(heroName in heroNames){
            }
        }
    }
}


data class AZhangSkill(val heroName:String,val skill:String,val desc:String)
