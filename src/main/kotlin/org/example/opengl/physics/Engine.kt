package org.example.opengl.physics

import org.example.opengl.constructor.Destroyable
import org.example.opengl.constructor.Manager
import org.joml.Math
import org.joml.Vector3f
import java.lang.IndexOutOfBoundsException

class Engine : Destroyable {
    val grid = Grid()
    val entities = Manager()

    fun create() {
        val radians = Math.toRadians(entities.stack.size.toFloat() / 10)
        val x = Math.sin(radians) / 1000f
        val y = Math.cos(radians) / 1000f
        val entity = Particle(x, y, 0.0f)
        clipToLimits(entity)
        entities.add(entity)
        val (a, b, c) = grid.getCoordinate(entity.position)
        grid.set(a, b, c, entity)
    }

    fun delete() {
        try {
            val entity = entities.stack.removeAt(0) as Particle
            val (x, y, z) = grid.getCoordinate(entity.position)
            grid.remove(x, y, z, entity)
        } catch (e: IndexOutOfBoundsException) {
            return
        }
    }

    fun clipToLimits(entity: Particle) {
        val position = entity.position
        if (position.x > 1) position.x = 1f
        if (position.y > 1) position.y = 1f
        if (position.z > 1) position.z = 1f
        if (position.x < -1) position.x = -1f
        if (position.y < -1) position.y = -1f
        if (position.z < -1) position.z = -1f
    }

    fun move(entity: Particle, displacement: Vector3f) {
        val previous = grid.getCoordinate(entity.position)
        entity.position.add(displacement)
        clipToLimits(entity)
        val current = grid.getCoordinate(entity.position)
        if (previous == current) return
        val (x, y, z) = previous
        grid.remove(x, y, z, entity)
        val (a, b, c) = current
        grid.set(a, b, c, entity)
    }

    fun step() {
        val particleIterator = entities.iterator()
        while (particleIterator.hasNext()) {
            val particle = particleIterator.next() as Particle
            val nearParticles = grid.getAroundTarget(particle.position, 2)

            for (other in nearParticles) {
                if (other == particle) continue
                val distance = particle.position.distance(other.position)
                val diameter = 0.2f
                if (distance < diameter && distance != 0f) {
                    val displacement = Vector3f(other.position).sub(particle.position).normalize()
                        .mul(0.5f * (diameter - distance))
                    move(other, displacement)
                    move(particle, displacement.negate())
                }
            }
        }
    }

    override fun destroy() {
        entities.destroy()
    }

}