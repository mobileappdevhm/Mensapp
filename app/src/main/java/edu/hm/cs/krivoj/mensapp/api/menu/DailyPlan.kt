package edu.hm.cs.krivoj.mensapp.api.menu

import java.io.Serializable
import java.time.LocalDate
import java.util.*

class DailyPlan(val date: LocalDate, private var dishes: MutableList<Dish>) : MutableList<Dish> by dishes, Serializable {
    constructor(date: LocalDate) : this(date, ArrayList<Dish>())
}
