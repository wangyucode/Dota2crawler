package cn.wycode.dota2crawler

import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Level
import org.apache.log4j.Logger
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.pipeline.ConsolePipeline
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

fun main(args: Array<String>) {
    BasicConfigurator.configure()
    Logger.getRootLogger().level = Level.INFO
//    crawlHeroList()
//    crawlHeroDetail()
    crawlPropList()
}


fun crawlPropList() {
    Spider.create(PropListProcessor())
            .addUrl("https://www.dota2.com.cn/items/index.htm")
            .addUrl("https://www.dota2.com/jsfeed/heropediadata?feeds=itemdata&v=47284738sc2aWpANpKA&l=schinese")
            .addPipeline(ConsolePipeline())
            .setSpiderListeners(listOf(ErrorListener()))
            .run()
}


fun crawlHeroDetail() {
    val connection = getDatabaseConnection()
    if (connection != null) {
        Spider.create(HeroDetailProcessor())
                .addUrl("https://www.dota2.com.cn/heroes/index.htm")
                .addPipeline(ConsolePipeline())
                .addPipeline(HeroDetailH2Pipeline(connection))
                .setSpiderListeners(listOf(ErrorListener()))
                .run()
    }
}


fun crawlHeroList() {
    val connection = getDatabaseConnection()
    if (connection != null) {
        //开启爬取
        Spider.create(HeroListPageProcessor())
                .addUrl("https://dota2-zh.gamepedia.com/%E8%8B%B1%E9%9B%84")
                .addPipeline(ConsolePipeline())
                .addPipeline(H2Pipeline(connection))
                .run()
    }
}

fun getDatabaseConnection(): Connection? {
    //连接数据库
    Class.forName("org.h2.Driver")
    val resourceBundle = ResourceBundle.getBundle("application")
    val url = resourceBundle.getString("datasource.url")
    println(url)
    val username = resourceBundle.getString("datasource.username")
    val password = resourceBundle.getString("datasource.password")
    return try {
        DriverManager.getConnection(url, username, password)
    } catch (se: SQLException) {
        se.printStackTrace()
        null
    }
}
