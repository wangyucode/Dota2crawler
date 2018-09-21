package cn.wycode.dota2crawler

import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.pipeline.ConsolePipeline

fun main(args: Array<String>) {
    Spider.create(HeroListPageProcessor())
            .addUrl("https://dota2-zh.gamepedia.com/%E8%8B%B1%E9%9B%84")
            .addPipeline(ConsolePipeline())
            .run()
}