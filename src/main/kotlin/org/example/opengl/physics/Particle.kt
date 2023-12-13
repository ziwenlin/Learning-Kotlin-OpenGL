package org.example.opengl.physics

import org.example.opengl.constructor.Destroyable
import org.joml.Vector3f

class Particle : Destroyable {
    val positionCurrent = Vector3f()
    val positionPrevious = Vector3f()
    val velocity = Vector3f()
    val acceleration = Vector3f()

    var diameter = 20f
    var energy = 0f
    var bind = 1f

    fun updatePosition(timeDelta: Float) {
        velocity.set(positionCurrent).sub(positionPrevious)
        positionPrevious.set(positionCurrent)
        acceleration.mul(timeDelta * timeDelta)
        positionCurrent.add(velocity).add(acceleration)
        acceleration.zero()
    }

    fun accelerate(acceleration: Vector3f) {
        this.acceleration.add(acceleration)
    }

    constructor() {

    }

    constructor(x: Float, y: Float, z: Float) {
        positionCurrent.set(x, y, z)
        positionPrevious.set(positionCurrent)
    }

    override fun destroy() {
        positionCurrent.zero()
        positionPrevious.zero()
        diameter = 0f
    }
}