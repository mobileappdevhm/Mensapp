package edu.hm.cs.krivoj.mensapp.api.price

import java.io.Serializable
import java.util.*

class Table : Serializable {

    private val map = HashMap<String, Category?>()

    fun put(key: String, value: Category): Category? =  map.put(key, value)

    private fun cleanKey(key: String): String = key.replace("(Aktionsgericht)|(Aktionsessen)|(Biogericht)|(Bioessen)".toRegex(), "Bio-/Aktionsgericht").trim { it <= ' ' }

    operator fun contains(key: String): Boolean = map.containsKey(cleanKey(key))

    fun getStudentPrice(key: String): String {
        if (!contains(key)) {
            return ""
        }
        val clean = cleanKey(key)
        return map[clean]!!.student
    }

    fun getEmployeePrice(key: String): String {
        if (!contains(key)) {
            return ""
        }
        val clean = cleanKey(key)
        return map[clean]!!.employee
    }

    fun getGuestPrice(key: String): String {
        if (!contains(key)) {
            return ""
        }
        val clean = cleanKey(key)
        return map[clean]!!.guest
    }
}
