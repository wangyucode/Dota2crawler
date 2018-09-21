package cn.wycode.dota2crawler

import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.pipeline.ConsolePipeline
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

fun main(args: Array<String>) {
    //连接数据库
    Class.forName("org.h2.Driver")
    val resourceBundle = ResourceBundle.getBundle("application")
    val url = resourceBundle.getString("datasource.url")
    println(url)
    val username = resourceBundle.getString("datasource.username")
    val password = resourceBundle.getString("datasource.password")
    val con = try {
        DriverManager.getConnection(url, username, password)
    } catch (se: SQLException) {
        se.printStackTrace()
        return
    }
    //开启爬取
    Spider.create(HeroListPageProcessor())
            .addUrl("https://dota2-zh.gamepedia.com/%E8%8B%B1%E9%9B%84")
            .addPipeline(ConsolePipeline())
            .addPipeline(H2Pipeline(con))
            .run()

}