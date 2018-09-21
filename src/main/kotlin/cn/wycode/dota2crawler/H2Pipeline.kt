package cn.wycode.dota2crawler

import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.sql.Connection
import java.sql.PreparedStatement


class H2Pipeline(con: Connection) : Pipeline {

    private val sql = "MERGE INTO DOTA2HERO(NAME,IMAGE_URL,TYPE) VALUES(?,?,?) "
    private val statement: PreparedStatement

    init {
        statement = con.prepareStatement(sql)
    }


    override fun process(resultItems: ResultItems, task: Task) {
        if (resultItems.all.isNotEmpty()) {
            val name  = resultItems.all.getValue("name").toString()
            statement.setString(1, name)
            statement.setString(2, resultItems.all.getValue("imageUrl").toString())
            statement.setString(3, resultItems.all.getValue("type").toString())
            if(statement.executeUpdate()>0){
                println("$name 插入成功！")
            }
        }
    }
}