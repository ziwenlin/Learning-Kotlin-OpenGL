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
    val (program, destroy) = createProgram()
    window.setExitCallback(destroy)
    window.loop(program)
    window.exit(0)
}
