package org.example.opengl.physics

import org.joml.Vector3f

class Grid {
    val grid = hashMapOf<Int, HashMap<Int, HashMap<Int, MutableList<Particle>>>>()

    fun getCoordinate(vector3f: Vector3f): Triple<Int, Int, Int> {
        val gridStep = 0.1f
        val x = (vector3f.x / gridStep).toInt()
        val y = (vector3f.y / gridStep).toInt()
        val z = (vector3f.z / gridStep).toInt()
        return Triple(x, y, z)
    }

    fun getAroundTarget(vector3f: Vector3f, distance: Int): MutableList<Particle> {
        val (x, y, z) = getCoordinate(vector3f)
        val particles = mutableListOf<Particle>()
        val distanceRange = -distance..distance
        for (indexX in distanceRange) {
            for (indexY in distanceRange) {
                for (indexZ in distanceRange) {
                    val grid = get(x + indexX, y + indexY, z)
                    particles.addAll(grid)
                }
            }
        }
        return particles
    }

    fun remove(x: Int, y: Int, z: Int, entity: Particle) {
        val gridList = get(x, y, z)
        gridList.remove(entity)
    }

    fun set(x: Int, y: Int, z: Int, entity: Particle) {
        val gridList = get(x, y, z)
        gridList.add(entity)
    }

    fun get(x: Int, y: Int, z: Int): MutableList<Particle> {
        var gridX = grid[x]
        if (gridX == null) {
            val particleList = mutableListOf<Particle>()
            gridX = hashMapOf(
                y to hashMapOf(
                    z to particleList
                )
            )
            grid[x] = gridX
            return particleList
        }
        var gridY = gridX.get(y)
        if (gridY == null) {
            val particleList = mutableListOf<Particle>()
            gridY = hashMapOf(
                z to particleList
            )
            gridX[y] = gridY
            return particleList
        }
        val gridZ = gridY[z]
        if (gridZ == null) {
            val particleList = mutableListOf<Particle>()
            gridY[z] = particleList
            return particleList
        }
        return gridZ
    }
}