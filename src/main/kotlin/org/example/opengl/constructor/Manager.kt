package org.example.opengl.constructor

class Manager : Destroyable {
    val stack = arrayListOf<Destroyable>()

    fun add(o: Destroyable): Destroyable {
        stack.add(o)
        return o
    }

    fun remove(o: Destroyable): Destroyable {
        stack.remove(o)
        return o
    }

    fun iterator(): MutableIterator<Destroyable> {
        return stack.iterator()
    }

    override fun destroy() {
        val stackIterator = stack.iterator()
        while (stackIterator.hasNext()) {
            val objectX = stackIterator.next()
            stackIterator.remove()
            objectX.destroy()
        }
    }
}