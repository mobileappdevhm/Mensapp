package edu.hm.cs.krivoj.mensapp.api.menu

import java.io.Serializable
import java.time.LocalDate
import java.util.ArrayList
import java.util.Comparator
import java.util.Spliterator
import java.util.function.Consumer
import java.util.function.Predicate
import java.util.function.UnaryOperator
import java.util.stream.Stream

class Menu(val begin: LocalDate, val end: LocalDate) : Serializable {

    private val dailyPlans = ArrayList<DailyPlan>()

    fun add(dailyPlan: DailyPlan) = dailyPlans.add(dailyPlan)
    fun remove(dailyPlan: DailyPlan) = dailyPlans.remove(dailyPlan)
    operator fun get(index: Int) = dailyPlans[index]
    val size get() = dailyPlans.size

    /**
     * Returns index of plan behind specified date. Returns -1 if none found.
     * @param date Date of wished plan
     * @return Index of plan behind date
     */
    fun indexOf(date: LocalDate): Int = dailyPlans.indexOfFirst { it.date == date || it.date.isAfter(date)}

}
