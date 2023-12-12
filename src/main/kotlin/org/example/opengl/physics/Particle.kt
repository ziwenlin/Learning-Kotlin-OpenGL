package org.example.opengl.physics

import org.example.opengl.constructor.Destroyable
import org.joml.Vector3f

class Particle : Destroyable {
    val position = Vector3f()
    val velocity = Vector3f()
    val energy = 0f
    val bind = 1f

    constructor()

    constructor(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
    }

    override fun destroy() {

    }
}