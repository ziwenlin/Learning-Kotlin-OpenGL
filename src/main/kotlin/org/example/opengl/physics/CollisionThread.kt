package org.example.opengl.physics

import org.example.opengl.constructor.Destroyable
import org.joml.Vector3f

class CollisionThread(name: String) : Destroyable, Thread(name) {
    val cells = mutableListOf<MutableList<Particle>>()
    val grids = mutableListOf<MutableList<Particle>>()

    override fun run() {
        while (cells.isNotEmpty()) {
            val cell = cells.removeFirst()
            val grid = grids.removeFirst()
            for (particle1 in cell) {
                for (particle2 in grid) {
                    if (particle2 == particle1) continue
                    calculateCollision(particle1, particle2)
                }
            }
        }
    }

    fun calculateCollision(particle1: Particle, particle2: Particle) {
        val collisionAxis = Vector3f(particle1.positionCurrent).sub(particle2.positionCurrent)
        val distance = collisionAxis.length()
        val diameter = (particle1.diameter + particle2.diameter) / 2
        if (distance < diameter && distance != 0.0f) {
            val direction = collisionAxis.div(distance)
            val ratio = particle2.weight / (particle1.weight + particle2.weight)
            val overlap = diameter - distance
            val displacement1 = ratio * overlap
            val displacement2 = (1.0f - ratio) * overlap

            particle1.positionCurrent.add(Vector3f(direction).mul(displacement1))
            particle2.positionCurrent.sub(Vector3f(direction).mul(displacement2))
        }
    }

    override fun destroy() {
        this.join()
        cells.clear()
        grids.clear()
    }

    fun add(cell: MutableList<Particle>, grid: MutableList<Particle>) {
        cells.add(cell)
        grids.add(grid)
    }
}