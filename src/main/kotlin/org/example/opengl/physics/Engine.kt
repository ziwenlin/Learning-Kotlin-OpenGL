package org.example.opengl.physics

import org.example.opengl.constructor.Destroyable
import org.example.opengl.constructor.Manager
import org.joml.Math
import org.joml.Vector3f
import java.lang.IndexOutOfBoundsException

class Engine(val frequency: Float) : Destroyable {
    val grid = Grid(50f)
    val entities = Manager()

    val gravity = Vector3f(0f, -1000f, 0f)
    val center = Vector3f(400f, 300f, 0f)

    fun create() {
        val radians = Math.toRadians(entities.stack.size.toFloat() * 5)
        val x = Math.sin(radians) * 50f + 400f
        val y = Math.cos(radians) * 50f + 400f
        val entity = Particle(x, y, -0.3f)
        entity.diameter = Math.cos(radians) * 12f + 30f
        clipToLimits(entity)
        entities.add(entity)
        val hashCode = grid.getHashCode(entity.positionPrevious)
        grid.set(hashCode, entity)
    }

    fun delete() {
        try {
            val entity = entities.stack.removeAt(0) as Particle
            val hashCode = grid.getHashCode(entity.positionPrevious)
            grid.remove(hashCode, entity)
            entity.destroy()
        } catch (e: IndexOutOfBoundsException) {
            return
        }
    }

    fun clipToLimits(entity: Particle) {
        val position = entity.positionCurrent
        val xLimit = 800f - entity.diameter
        val yLimit = 600f - entity.diameter
        val xyLimit = entity.diameter
        if (position.x > xLimit) position.x = xLimit
        if (position.y > yLimit) position.y = yLimit
        if (position.z > 1) position.z = 1f
        if (position.x < xyLimit) position.x = xyLimit
        if (position.y < xyLimit) position.y = xyLimit
        if (position.z < -1) position.z = -1f
    }

    fun step() {
        val sub_step = 8
        val sub_tick = 1 / frequency / sub_step
        for (i in 0 until sub_step) {
            applyGravity()
            solveCollisions()
            applyLimits()
            updatePositions(sub_tick)
        }
    }

    fun updatePositions(timeDelta: Float) {
        val particleIterator = entities.iterator()
        while (particleIterator.hasNext()) {
            val particle = particleIterator.next() as Particle
            val positionPrevious = Vector3f(particle.positionPrevious)
            particle.updatePosition(timeDelta)
            val positionCurrent = Vector3f(particle.positionCurrent)
            grid.update(positionCurrent, positionPrevious, particle)
        }
    }

    fun applyLimits() {
        val particleIterator = entities.iterator()
        while (particleIterator.hasNext()) {
            val particle = particleIterator.next() as Particle
            clipToLimits(particle)
        }
    }

    fun applyGravity() {
        val particleIterator = entities.iterator()
        while (particleIterator.hasNext()) {
            val particle = particleIterator.next() as Particle
            particle.accelerate(gravity)
        }
    }

    fun solveCollisions() {
        val particleIterator = entities.iterator()
        while (particleIterator.hasNext()) {
            val particle = particleIterator.next() as Particle
            val nearParticles = grid.getAroundTarget(particle.positionCurrent, 1)

            for (other in nearParticles) {
                if (other == particle) continue
                val collisionAxis = Vector3f(particle.positionCurrent).sub(other.positionCurrent)
                val distance = collisionAxis.distance(0f, 0f, 0f)
                val diameter = (particle.diameter + other.diameter) / 2
                if (distance < diameter && distance != 0f) {
                    val displacement = collisionAxis.div(distance)
                        .mul(0.5f * (diameter - distance))
                    particle.positionCurrent.add(displacement)
                    other.positionCurrent.sub(displacement)
                }
            }
        }
    }


    override fun destroy() {
        entities.destroy()
    }

}