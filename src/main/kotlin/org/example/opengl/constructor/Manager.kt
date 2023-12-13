package org.example.opengl.constructor

class Manager : Destroyable {
    val stack = arrayListOf<Destroyable>()

    private var cycleCounter = 0

    fun add(o: Destroyable): Destroyable {
        stack.add(o)
        return o
    }

    fun remove(o: Destroyable): Destroyable {
        stack.remove(o)
        return o
    }

    fun cycle(): Destroyable {
        if (cycleCounter + 1 < stack.size) {
            cycleCounter += 1
        } else {
            cycleCounter = 0
        }
        return stack[cycleCounter]
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