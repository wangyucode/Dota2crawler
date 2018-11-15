package cn.wycode.dota2crawler

import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.sql.Connection
import java.sql.PreparedStatement


class HeroDetailH2Pipeline(con: Connection) : Pipeline {

    private val iconSql ="UPDATE DOTA2HERO SET ICON=? WHERE NAME=?"
//    private val detailSql = "MERGE INTO HERO_DETAIL(NAME,ATTACK_TYPE,OTHER_NAME,STRENGTH_START,STRENGTH_GROW,AGILITY_START,AGILITY_GROW,INTELLIGENCE_START,INTELLIGENCE_GROW,ATTACK_POWER,ATTACK_SPEED,ARMOR,SPEED,STORY,TALENT25LEFT,TALENT25RIGHT,TALENT20LEFT,TALENT20RIGHT,TALENT15LEFT,TALENT15RIGHT,TALENT10LEFT,TALENT10RIGHT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
//    private val abilitySql = "MERGE INTO HERO_ABILITY(NAME,ANNOTATION,COOL_DOWN,DESCRIPTION,HERO_NAME,IMAGE_URL,MAGIC_CONSUMPTION,TIPS) VALUES(?,?,?,?,?,?,?,?) "
//    private val attributeSql = "MERGE INTO HERO_ABILITY_ATTRIBUTES(HERO_ABILITY_NAME, ATTRIBUTES, ATTRIBUTES_KEY) VALUES(?,?,?) "
    private val iconStatement: PreparedStatement
//    private val detailStatement: PreparedStatement
//    private val abilityStatement: PreparedStatement
//    private val attributeStatement: PreparedStatement

    init {
        iconStatement = con.prepareStatement(iconSql)
//        detailStatement = con.prepareStatement(detailSql)
//        abilityStatement = con.prepareStatement(abilitySql)
//        attributeStatement = con.prepareStatement(attributeSql)
    }


    override fun process(resultItems: ResultItems, task: Task) {
        if (resultItems.all.isNotEmpty()) {
            val name = resultItems.all.getValue("name") as String

            iconStatement.setString(1,resultItems.all.getValue("icon") as String)
            iconStatement.setString(2,name)

            if (iconStatement.executeUpdate() > 0) {
                println("$name icon更新成功！")
            }

//            detailStatement.setString(1, name)
//            detailStatement.setString(2, resultItems.all.getValue("attackType") as String)
//            detailStatement.setString(3, resultItems.all.getValue("otherName") as String)
//            detailStatement.setInt(4, resultItems.all.getValue("strengthStart") as Int)
//            detailStatement.setString(5, resultItems.all.getValue("strengthGrow") as String)
//            detailStatement.setInt(6, resultItems.all.getValue("agilityStart") as Int)
//            detailStatement.setString(7, resultItems.all.getValue("agilityGrow") as String)
//            detailStatement.setInt(8, resultItems.all.getValue("intelligenceStart") as Int)
//            detailStatement.setString(9, resultItems.all.getValue("intelligenceGrow") as String)
//            detailStatement.setInt(10, resultItems.all.getValue("attackPower") as Int)
//            detailStatement.setInt(11, resultItems.all.getValue("attackSpeed") as Int)
//            detailStatement.setInt(12, resultItems.all.getValue("armor") as Int)
//            detailStatement.setInt(13, resultItems.all.getValue("speed") as Int)
//            detailStatement.setString(14, resultItems.all.getValue("story") as String)
//            detailStatement.setString(15, resultItems.all.getValue("talent25Left") as String)
//            detailStatement.setString(16, resultItems.all.getValue("talent25Right") as String)
//            detailStatement.setString(17, resultItems.all.getValue("talent20Left") as String)
//            detailStatement.setString(18, resultItems.all.getValue("talent20Right") as String)
//            detailStatement.setString(19, resultItems.all.getValue("talent15Left") as String)
//            detailStatement.setString(20, resultItems.all.getValue("talent15Right") as String)
//            detailStatement.setString(21, resultItems.all.getValue("talent10Left") as String)
//            detailStatement.setString(22, resultItems.all.getValue("talent10Right") as String)
//            if (detailStatement.executeUpdate() > 0) {
//                println("$name 插入成功！")
//                val abilities = resultItems.all.getValue("abilities") as List<HeroAbility>
//                for (ability in abilities) {
//                    abilityStatement.setString(1, ability.name)
//                    abilityStatement.setString(2, ability.annotation)
//                    abilityStatement.setString(3, ability.coolDown)
//                    abilityStatement.setString(4, ability.description)
//                    abilityStatement.setString(5, ability.heroName)
//                    abilityStatement.setString(6, ability.imageUrl)
//                    abilityStatement.setString(7, ability.magicConsumption)
//                    abilityStatement.setString(8, ability.tips)
//
//                    if (abilityStatement.executeUpdate() > 0) {
//                        println("${ability.name} 插入成功！")
//                        for (attr in ability.attributes){
//                            attributeStatement.setString(1, ability.name)
//                            attributeStatement.setString(2, attr.value)
//                            attributeStatement.setString(3, attr.key)
//                            if (attributeStatement.executeUpdate() > 0) {
//                                println("${attr.key} 插入成功！")
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}