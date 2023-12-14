package org.example.opengl.physics

import org.joml.Vector3f

class ArrayGrid(gridLength: Float, gridStep: Float) {

    private val gridSize = (gridLength / gridStep).toInt()
    private val gridMiddle = gridSize / 2

    val array = Array(gridSize) {
        Array(gridSize) {
            Array(gridSize) {
                mutableListOf<Particle>()
            }
        }
    }

    fun getCoordinate(vector3f: Vector3f): Triple<Int, Int, Int> {
        val x = (vector3f.x / gridMiddle).toInt() + gridMiddle
        val y = (vector3f.y / gridMiddle).toInt() + gridMiddle
        val z = (vector3f.z / gridMiddle).toInt() + gridMiddle
        return Triple(x, y, z)
    }

    fun getAroundGrid(vector3f: Vector3f, distance: Int): MutableList<MutableList<Particle>> {
        val (x, y, z) = getCoordinate(vector3f)
        return getAroundGrid(x, y, z, distance)
    }

    fun getAroundGrid(x: Int, y: Int, z: Int, distance: Int): MutableList<MutableList<Particle>> {
        val particles = mutableListOf<MutableList<Particle>>()
        val distanceRange = -distance..distance
        for (indexX in distanceRange) {
            for (indexY in distanceRange) {
                for (indexZ in distanceRange) {
                    val grid = get(x + indexX, y + indexY, z + indexZ)
                    particles.add(grid)
                }
            }
        }
        return particles
    }

    fun update(positionCurrent: Vector3f, positionPrevious: Vector3f, entity: Particle) {
        val current = getCoordinate(positionCurrent)
        val previous = getCoordinate(positionPrevious)
        if (current == previous) {
            return
        }
        remove(previous.first, previous.second, previous.third, entity)
        set(current.first, current.second, current.third, entity)
    }

    fun remove(x: Int, y: Int, z: Int, entity: Particle) {
        val gridList = get(x, y, z)
        while (gridList.remove(entity) == true) {
            continue
        }
    }

    fun set(x: Int, y: Int, z: Int, entity: Particle) {
        val list = array[x][y][z]
        list.add(entity)
    }

    fun get(x: Int, y: Int, z: Int): MutableList<Particle> {
        return array[x][y][z]
    }

}