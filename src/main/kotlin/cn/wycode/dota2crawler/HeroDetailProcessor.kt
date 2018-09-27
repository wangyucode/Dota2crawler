package cn.wycode.dota2crawler

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor


class HeroDetailProcessor : PageProcessor {

    private val site: Site = Site.me().setSleepTime((Math.random() * 500 + 1000).toInt())

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        if (page != null) {
            if (page.url.get() == "https://www.dota2.com.cn/heroes/index.htm") {
                val detailLink = page.html.xpath("//a[@class='heroPickerIconLink']/@href").all()
                println("共${detailLink.size}条链接")
                page.addTargetRequests(detailLink)
            } else {
                //info
                val heroInfo = page.html.xpath("//div[@class='hero_info']")
                val heroName = heroInfo.xpath("//div[@class='hero_name']/text()").get().trim()
                page.putField("name", heroName)

                val infoList = heroInfo.xpath("//li[@class='clearfix']").nodes()
                val attackType = infoList[0].xpath("//p/span/text()").get().trim()
                page.putField("attackType", attackType)
                //TODO 爬取官网可以在列表直接获取到阵营，所以在详情去掉此字段
//                val camp = infoList[2].xpath("//p/text()").get().trim()
//                page.putField("camp", camp)

                val otherName = infoList[3].xpath("//p/text()").get().trim()
                page.putField("otherName", otherName)
                //property
                val properties = page.html.xpath("//div[@class='property_box']/ul[@class='pro6_box']")
                val strength = properties.xpath("//li[1]/text()").get().trim()
                val strengthSplit = strength.split("+")
                page.putField("strengthStart", strengthSplit[0].trim().toInt())
                page.putField("strengthGrow", strengthSplit[1].trim())

                val agility = properties.xpath("//li[2]/text()").get().trim()
                val agilitySplit = agility.split("+")
                page.putField("agilityStart", agilitySplit[0].trim().toInt())
                page.putField("agilityGrow", agilitySplit[1].trim())

                val intelligence = properties.xpath("//li[3]/text()").get().trim()
                val intelligenceSplit = intelligence.split("+")
                page.putField("intelligenceStart", intelligenceSplit[0].trim().toInt())
                page.putField("intelligenceGrow", intelligenceSplit[1].trim())

                val attackPower = properties.xpath("//li[4]/text()").get().trim()
                page.putField("attackPower", attackPower.toInt())

                val attackSpeed = properties.xpath("//li[4]/div[2]/p/span[1]/text()").get().trim()
                page.putField("attackSpeed", attackSpeed.toInt())

                val armor = properties.xpath("//li[5]/text()").get().trim()
                page.putField("armor", armor.toInt())

                val speed = properties.xpath("//li[6]/text()").get().trim()
                page.putField("speed", speed.toInt())
                //story
                val story = page.html.xpath("//div[@class='story_box']/text()").get().trim()
                page.putField("story", story)
                //talent
                val talentBox = page.html.xpath("//ul[@class='talent_ul']")
                val talent25Left = talentBox.xpath("//li[1]/div[1]/text()").get().trim()
                val talent25Right = talentBox.xpath("//li[1]/div[3]/text()").get().trim()
                page.putField("talent25Left", talent25Left)
                page.putField("talent25Right", talent25Right)

                val talent20Left = talentBox.xpath("//li[2]/div[1]/text()").get().trim()
                val talent20Right = talentBox.xpath("//li[2]/div[3]/text()").get().trim()
                page.putField("talent20Left", talent20Left)
                page.putField("talent20Right", talent20Right)

                val talent15Left = talentBox.xpath("//li[3]/div[1]/text()").get().trim()
                val talent15Right = talentBox.xpath("//li[3]/div[3]/text()").get().trim()
                page.putField("talent15Left", talent15Left)
                page.putField("talent15Right", talent15Right)

                val talent10Left = talentBox.xpath("//li[4]/div[1]/text()").get().trim()
                val talent10Right = talentBox.xpath("//li[4]/div[3]/text()").get().trim()
                page.putField("talent10Left", talent10Left)
                page.putField("talent10Right", talent10Right)
                //ability
                val skillBoxes = page.html.xpath("//dl[@id='focus_dl']/dd").nodes()
                val abilities = ArrayList<HeroAbility>()
                for (skillBox in skillBoxes) {

                    val name = skillBox.xpath("//p[@class='skill_intro']/span/text()").get().trim()
                    val description = skillBox.xpath("//p[@class='skill_intro']/text()").get().trim()
                    val imageUrl = skillBox.xpath("//img[@class='skill_b']/@src").get()
                    val annotation = skillBox.xpath("//div[@class='skill_bot']/text()").get().trim()
                    val tips = skillBox.xpath("//p[@class='color_green']/html()")?.get()?.trim()?.replace("<br>", "\n")
                            ?: ""
                    val magicConsumptionText = skillBox.xpath("//div[@class='icon_xh']/text()").get()
                    val magicConsumption = when {
                        magicConsumptionText.contains(':') -> magicConsumptionText.split(':')[1].trim()
                        magicConsumptionText.contains('：') -> magicConsumptionText.split('：')[1].trim()
                        else -> magicConsumptionText
                    }
                    val coolDownText = skillBox.xpath("//div[@class='icon_lq']/text()").get()
                    val coolDown = when {
                        coolDownText.contains(':') -> coolDownText.split(':')[1]
                        coolDownText.contains('：') -> coolDownText.split('：')[1]
                        else -> coolDownText
                    }
                    val skillAttrs = skillBox.xpath("//div[@class='skill_info']/ul/li").nodes()
                    val abilityAttrs = HashMap<String, String>()
                    for (skillAttr in skillAttrs) {
                        val keyText = skillAttr.xpath("//span/text()").get().trim().replace(":", "").replace("：", "")
                        val attr = skillAttr.xpath("//li/text()").get().trim()
                        abilityAttrs[keyText] = attr
                    }
                    val heroAbility = HeroAbility(name, heroName, imageUrl, annotation, description, magicConsumption, coolDown, tips, abilityAttrs)
                    println(heroAbility.toString())
                    abilities.add(heroAbility)
                }
                page.putField("abilities", abilities)
            }
        }
    }
}

data class HeroAbility(
        val name: String = "",
        val heroName: String = "",
        val imageUrl: String = "",
        val annotation: String = "",
        val description: String = "",
        val magicConsumption: String = "",
        val coolDown: String = "",
        val tips: String = "",
        val attributes: Map<String, String> = HashMap()
)