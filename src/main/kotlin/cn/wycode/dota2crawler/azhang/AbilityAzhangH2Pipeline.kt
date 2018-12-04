package cn.wycode.dota2crawler.azhang

import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.sql.Connection
import java.sql.PreparedStatement


class AbilityAzhangH2Pipeline(con: Connection) : Pipeline {

    private val noAzhangHeroSql = "INSERT INTO DOTA_AZHANG_EFFECT(ID,ABILITY,DESC,ICON,TYPE,HERO_NAME) values(?,?,?,?,?,?)"
    private val noAzhangHeroStatement: PreparedStatement

    private var id = 1

    init {
        noAzhangHeroStatement = con.prepareStatement(noAzhangHeroSql)
    }


    override fun process(resultItems: ResultItems, task: Task) {
        if (resultItems.all.isNotEmpty()) {
            val upgradeSkills= resultItems.all.getValue("upgradeSkills") as List<*>
            for (upgradeSkill in upgradeSkills) {
                upgradeSkill as AZhangSkill
                noAzhangHeroStatement.setLong(1, id.toLong())
                noAzhangHeroStatement.setString(2, upgradeSkill.skill)
                noAzhangHeroStatement.setString(3, upgradeSkill.desc)
                noAzhangHeroStatement.setString(4, upgradeSkill.icon)
                noAzhangHeroStatement.setInt(5, upgradeSkill.type)
                noAzhangHeroStatement.setString(6, upgradeSkill.heroName)
                noAzhangHeroStatement.execute()
                println("$id 插入成功！")
                id++
            }

            val addSkills= resultItems.all.getValue("addSkills") as List<*>
            for (addSkill in addSkills) {
                addSkill as AZhangSkill
                noAzhangHeroStatement.setLong(1, id.toLong())
                noAzhangHeroStatement.setString(2, addSkill.skill)
                noAzhangHeroStatement.setString(3, addSkill.desc)
                noAzhangHeroStatement.setString(4, addSkill.icon)
                noAzhangHeroStatement.setInt(5, addSkill.type)
                noAzhangHeroStatement.setString(6, addSkill.heroName)
                noAzhangHeroStatement.execute()
                println("$id 插入成功！")
                id++
            }

            val otherSkills= resultItems.all.getValue("otherSkills") as List<*>
            for (otherSkill in otherSkills) {
                otherSkill as AZhangSkill
                noAzhangHeroStatement.setLong(1, id.toLong())
                noAzhangHeroStatement.setString(2, otherSkill.skill)
                noAzhangHeroStatement.setString(3, otherSkill.desc)
                noAzhangHeroStatement.setString(4, otherSkill.icon)
                noAzhangHeroStatement.setInt(5, otherSkill.type)
                noAzhangHeroStatement.setString(6, otherSkill.heroName)
                noAzhangHeroStatement.execute()
                println("$id 插入成功！")
                if(id==101){
                    println("$id 插入成功！")
                }
                id++

            }
        }
    }
}