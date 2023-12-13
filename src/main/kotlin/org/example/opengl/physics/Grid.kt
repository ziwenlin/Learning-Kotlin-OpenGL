package org.example.opengl.physics

import org.joml.Vector3f

class Grid(val gridStep: Float) {
    val grid = hashMapOf<String, MutableList<Particle>>()
    val hash = hashMapOf<Particle, String>()

    fun getCoordinate(vector3f: Vector3f): Triple<Int, Int, Int> {
        val x = (vector3f.x / gridStep).toInt()
        val y = (vector3f.y / gridStep).toInt()
        val z = (vector3f.z / gridStep).toInt()
        return Triple(x, y, z)
    }

    fun getCoordinate(hashCode: String): Triple<Int, Int, Int> {
        val (x, y, z) = hashCode.split(' ')
        return Triple(x.toInt(), y.toInt(), z.toInt())
    }

    fun getHashCode(vector3f: Vector3f): String {
        val x = (vector3f.x / gridStep).toInt()
        val y = (vector3f.y / gridStep).toInt()
        val z = (vector3f.z / gridStep).toInt()
        return "$x $y $z"
    }

    fun getHashCode(x: Int, y: Int, z: Int): String {
        return "$x $y $z"
    }

    fun getAroundTarget(vector3f: Vector3f, distance: Int): MutableList<Particle> {
        val (x, y, z) = getCoordinate(vector3f)
        val particles = mutableListOf<Particle>()
        val distanceRange = -distance..distance
        for (indexX in distanceRange) {
            for (indexY in distanceRange) {
                for (indexZ in distanceRange) {
                    val hashCode = getHashCode(x + indexX, y + indexY, z)
                    val grid = get(hashCode)
                    particles.addAll(grid)
                }
            }
        }
        return particles
    }

    fun update(current: Vector3f, previous: Vector3f, entity: Particle) {
        val hashCodePrevious = getHashCode(previous)
        val hashCodeCurrent = getHashCode(current)
        if (hashCodeCurrent == hashCodePrevious) {
            return
        }
        remove(hashCodePrevious, entity)
        set(hashCodeCurrent, entity)
    }

    fun remove(hashCode: String, entity: Particle) {
        val hashCodeStored = hash[entity] ?: hashCode
        val gridList = get(hashCodeStored)
        while (gridList.remove(entity) == true) {
            continue
        }
    }

    fun set(hashCode: String, entity: Particle) {
        val list = grid[hashCode]
        if (list != null) list.add(entity)
        else grid[hashCode] = mutableListOf(entity)
        hash[entity] = hashCode
    }

    fun get(hashCode: String): MutableList<Particle> {
        val list = grid[hashCode]
        if (list == null) {
            println("Hashcode failed $hashCode")
            val list1 = mutableListOf<Particle>()
            grid[hashCode] = list1
            return list1
        }
        return list
    }
}