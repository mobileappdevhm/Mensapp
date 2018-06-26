package edu.hm.cs.krivoj.mensapp.api

import java.io.Serializable

class Canteen(val name: String, val location: String, val url: String) : Serializable, Comparable<Canteen> {

    override fun compareTo(other: Canteen): Int = name.compareTo(other.name)

}
