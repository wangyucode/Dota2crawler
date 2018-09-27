package cn.wycode.dota2crawler

import us.codecraft.webmagic.Request
import us.codecraft.webmagic.SpiderListener

class ErrorListener : SpiderListener {
    override fun onSuccess(request: Request?) {
        println("success on request:${request?.url}")
    }

    override fun onError(request: Request?) {
        println("error on request:${request?.url}")
    }
}