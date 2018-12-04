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
            var skillRichs = upgradeHtml.xpath("//li[@class='skilllist-rich']").all()
            val upgradeSkills = ArrayList<AZhangSkill>(skillRichs.size)
            for(skillRich in skillRichs){
                val skillRichHtml = Html(skillRich)
                val heroName = skillRichHtml.xpath("//div[@class='skilllist-rich-head']/a[1]/text()").get()
                val skill = skillRichHtml.xpath("//div[@class='skilllist-rich-head']/a[2]/text()").get()
                val desc = skillRichHtml.xpath("//div[@class='skilllist-rich-desc']/i/text()").get()
                val icon = skillRichHtml.xpath("//div[@class='skilllist-rich-image']/a/img/@src").get()
                val type = 1
                val aZhangSkill = AZhangSkill(heroName,skill,desc,icon,type)
                upgradeSkills.add(aZhangSkill)
            }
            page.putField("upgradeSkills",upgradeSkills)

            val addHtml = Html(content[1])
            skillRichs = addHtml.xpath("//li[@class='skilllist-rich']").all()
            val addSkills = ArrayList<AZhangSkill>(skillRichs.size)
            for(skillRich in skillRichs){
                val skillRichHtml = Html(skillRich)
                val heroName = skillRichHtml.xpath("//div[@class='skilllist-rich-head']/a[1]/text()").get()
                val skill = skillRichHtml.xpath("//div[@class='skilllist-rich-head']/a[2]/text()").get()
                val desc = skillRichHtml.xpath("//div[@class='skilllist-rich-desc']/i/text()").get()
                val icon = skillRichHtml.xpath("//div[@class='skilllist-rich-image']/a/img/@src").get()
                val type = 2
                val aZhangSkill = AZhangSkill(heroName,skill,desc,icon,type)
                addSkills.add(aZhangSkill)
            }
            page.putField("addSkills",addSkills)

            val otherHtml = Html(content[2])
            skillRichs = otherHtml.xpath("//li[@class='skilllist-rich']").all()
            val otherSkills = ArrayList<AZhangSkill>(skillRichs.size)
            for(skillRich in skillRichs){
                val skillRichHtml = Html(skillRich)
                val heroName = skillRichHtml.xpath("//div[@class='skilllist-rich-head']/a[1]/text()").get()
                val skill = skillRichHtml.xpath("//div[@class='skilllist-rich-head']/a[2]/text()").get()?:""
                val desc = skillRichHtml.xpath("//div[@class='skilllist-rich-desc']/i/text()").get()
                val icon = skillRichHtml.xpath("//div[@class='skilllist-rich-image']/a/img/@src").get()
                val type = 3
                val aZhangSkill = AZhangSkill(heroName,skill,desc,icon,type)
                otherSkills.add(aZhangSkill)
            }
            page.putField("otherSkills",otherSkills)
        }
    }
}


data class AZhangSkill(val heroName:String,val skill:String,val desc:String,val icon:String,val type:Int)
