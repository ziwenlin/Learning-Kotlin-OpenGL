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

    window.init()
    val program = createProgram()
    window.loop(program)
    window.exit(0)
}
