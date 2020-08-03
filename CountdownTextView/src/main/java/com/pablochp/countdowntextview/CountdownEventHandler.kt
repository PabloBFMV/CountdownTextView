package com.pablochp.countdowntextview

interface CountdownEventHandler {
    fun onTick(time: Long)
    fun onFinish()
}