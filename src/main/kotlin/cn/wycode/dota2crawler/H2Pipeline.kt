package cn.wycode.dota2crawler

import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.sql.Connection


class H2Pipeline(val con: Connection) : Pipeline {


    override fun process(resultItems: ResultItems, task: Task) {
        if(resultItems.all.isNotEmpty()){

        }
    }
}