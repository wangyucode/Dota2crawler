package cn.wycode.dota2crawler

import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import java.sql.Connection
import java.sql.PreparedStatement


class PropDetailH2Pipeline(con: Connection) : Pipeline {

    private val sql = "MERGE INTO DOTA_ITEM(KEY,CD,CNAME,COST,IMG,LORE,MC,NAME,NOTES,TYPE) VALUES(?,?,?,?,?,?,?,?,?,?) "
    private val componentSql = "INSERT INTO DOTA_ITEM_COMPONENTS(DOTA_ITEM_KEY, COMPONENTS) VALUES(?,?) "
    private val attrSql = "MERGE INTO DOTA_ITEM_ATTRS(DOTA_ITEM_KEY, ATTRS,ATTRS_KEY) VALUES(?,?,?) "
    private val descSql = "MERGE INTO DOTA_ITEM_DESC(DOTA_ITEM_KEY, DESC,DESC_KEY) VALUES(?,?,?) "

    private val statement: PreparedStatement
    private val componentStatement: PreparedStatement
    private val attrStatement: PreparedStatement
    private val descStatement: PreparedStatement

    init {
        statement = con.prepareStatement(sql)
        componentStatement = con.prepareStatement(componentSql)
        attrStatement = con.prepareStatement(attrSql)
        descStatement = con.prepareStatement(descSql)
    }


    override fun process(resultItems: ResultItems, task: Task) {
        if (resultItems.all.isNotEmpty()) {
            val items = resultItems.all.getValue("items") as ArrayList<Item>
            for (item in items) {
                statement.setString(1, item.key)
                statement.setInt(2, item.cd)
                statement.setString(3, item.cname)
                statement.setInt(4, item.cost)
                statement.setString(5, item.img)
                statement.setString(6, item.lore)
                statement.setString(7, item.mc)
                statement.setString(8, item.name)
                statement.setString(9, item.notes)
                statement.setString(10, item.type)
                if (statement.executeUpdate() > 0) {
                    println("${item.name} 插入成功！")

                    for (component in item.components) {
                        componentStatement.setString(1, item.key)
                        componentStatement.setString(2, component)
                        if (componentStatement.executeUpdate() > 0) {
                            println("$component 插入成功！")
                        }
                    }

                    for (attr in item.attrs) {
                        attrStatement.setString(1, item.key)
                        attrStatement.setString(2, attr.value)
                        attrStatement.setString(3, attr.key)
                        if (attrStatement.executeUpdate() > 0) {
                            println("${attr.key} 插入成功！")
                        }
                    }

                    for(desc in item.desc){
                        descStatement.setString(1, item.key)
                        descStatement.setString(2, desc.value)
                        descStatement.setString(3, desc.key)
                        if (descStatement.executeUpdate() > 0) {
                            println("${desc.key} 插入成功！")
                        }
                    }
                }
            }
        }
    }
}