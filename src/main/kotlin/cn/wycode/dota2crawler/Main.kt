package cn.wycode.dota2crawler

import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) {
    val resourceBundle = ResourceBundle.getBundle("application")
    val url = resourceBundle.getString("datasource.url")
    println(url)
//    val url = "jdbc:h2:tcp://localhost/H2DB/wycode"
//    val username = "wayne"
//    val password = "11900503"
//    val con = try {
//        DriverManager.getConnection(url, username, password)
//    } catch (se: SQLException) {
//        se.printStackTrace()
//        return
//    }
//    Spider.create(HeroListPageProcessor())
//            .addUrl("https://dota2-zh.gamepedia.com/%E8%8B%B1%E9%9B%84")
//            .addPipeline(ConsolePipeline())
//            .addPipeline(H2Pipeline(con))
//            .run()

}