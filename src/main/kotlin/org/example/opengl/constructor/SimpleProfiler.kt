package org.example.opengl.constructor

import org.example.opengl.renderer.Window

class SimpleProfiler(val window: Window) {
    var timeStart = 0.0f
    var timeDelta = 0.0f

    fun start() {
        timeStart = window.getTime()
    }

    fun stop(): Float {
        timeDelta = window.getTime() - timeStart
        return timeDelta
    }
}