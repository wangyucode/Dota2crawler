package cn.wycode.dota2crawler

import cn.wycode.dota2crawler.azhang.AbilityAzhangH2Pipeline
import cn.wycode.dota2crawler.azhang.AbilityAzhangProcessor
import cn.wycode.dota2crawler.azhang.HeroAzhangH2Pipeline
import cn.wycode.dota2crawler.azhang.HeroAzhangProcessor
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.pipeline.ConsolePipeline
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

fun main(args: Array<String>) {
//    BasicConfigurator.configure()
//    Logger.getRootLogger().level = Level.INFO
//    crawlHeroList()
//    crawlHeroDetail()
//    crawlPropList()
//    crawlHeroAzhangEffect()
    crawlAzhangEffect()
}


fun crawlPropList() {
    val connection = getDatabaseConnection()
    if (connection != null) {
        Spider.create(PropListProcessor())
                .addUrl("http://www.dota2.com/jsfeed/heropediadata?feeds=itemdata&v=47284738sc2aWpANpKA&l=schinese")
                .addUrl("http://www.dota2.com/items/")
                .addPipeline(PropDetailH2Pipeline(connection))
                .setSpiderListeners(listOf(ErrorListener()))
                .run()
    }
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

fun crawlHeroAzhangEffect() {
    val connection = getDatabaseConnection()
    if (connection != null) {
        Spider.create(HeroAzhangProcessor())
                .addUrl("https://dota2-zh.gamepedia.com/%E9%98%BF%E5%93%88%E5%88%A9%E5%A7%86%E7%A5%9E%E6%9D%96/%E5%8D%87%E7%BA%A7%E7%9A%84%E6%8A%80%E8%83%BD")
                .addPipeline(ConsolePipeline())
                .addPipeline(HeroAzhangH2Pipeline(connection))
                .setSpiderListeners(listOf(ErrorListener()))
                .run()
    }
}

fun crawlAzhangEffect() {
    val connection = getDatabaseConnection()
    if (connection != null) {
        Spider.create(AbilityAzhangProcessor())
                .addUrl("https://dota2-zh.gamepedia.com/%E9%98%BF%E5%93%88%E5%88%A9%E5%A7%86%E7%A5%9E%E6%9D%96/%E5%8D%87%E7%BA%A7%E7%9A%84%E6%8A%80%E8%83%BD")
                .addPipeline(ConsolePipeline())
                .addPipeline(AbilityAzhangH2Pipeline(connection))
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
