package org.example.opengl

import org.example.opengl.renderer.Window

/*
* Main
*
* */

val window = Window()
val width: Int = 1280
val height: Int = 720

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    window.init(width, height)
    val program = Program()
    window.setExitCallback({ program.destroy() })
    window.loop({ program.run() })
    window.exit(0)
}
