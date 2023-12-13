package org.example.opengl.physics

import org.example.opengl.constructor.Destroyable
import org.example.opengl.constructor.Manager
import org.joml.Math
import org.joml.Vector3f
import java.lang.IndexOutOfBoundsException

class Engine(val frequency: Float) : Destroyable {
    val grid = HashGrid(20f)
    val entities = Manager()

    val gravity = Vector3f(0f, -1000f, 0f)
    val center = Vector3f(400f, 300f, 0f)

    fun create() {
        val radians = Math.toRadians(entities.stack.size.toFloat() * 5)
        val x = Math.sin(radians) * 50f + 400f
        val y = Math.cos(radians) * 50f + 400f
        val entity = Particle(x, y, -0.8f)
        entity.diameter = Math.cos(radians) * 3f + 7f
        entity.weight = Math.cos(radians) * 5f + 7f
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
            particle.updatePosition(timeDelta)
            grid.update(particle.positionCurrent, particle)
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
        val cell = grid.grid.iterator()
        while (cell.hasNext()) {
            val (key, list) = cell.next()
            val nearParticles = grid.getAroundTarget(key, 1)
            for (particle1 in list) {
                for (particle2 in nearParticles) {
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
        entities.destroy()
    }

}