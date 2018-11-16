package cn.wycode.dota2crawler.azhang

import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.sql.Connection
import java.sql.PreparedStatement


class HeroAzhangH2Pipeline(con: Connection) : Pipeline {

    private val noAzhangHeroSql = "INSERT INTO DOTA_SPECIAL_HERO values(?)"
    private val noAzhangHeroStatement: PreparedStatement

    init {
        noAzhangHeroStatement = con.prepareStatement(noAzhangHeroSql)
    }


    override fun process(resultItems: ResultItems, task: Task) {
        if (resultItems.all.isNotEmpty()) {
            val names = resultItems.all.getValue("names") as List<*>
            for (name in names) {
                noAzhangHeroStatement.setString(1, name as String)
                noAzhangHeroStatement.execute()
                println("$name 插入成功！")
            }
        }
    }
}