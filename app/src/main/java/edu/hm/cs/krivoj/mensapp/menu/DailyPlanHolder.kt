package edu.hm.cs.krivoj.mensapp.menu

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import edu.hm.cs.krivoj.mensapp.R
import edu.hm.cs.krivoj.mensapp.api.menu.DailyPlan
import edu.hm.cs.krivoj.mensapp.api.price.Table
import java.time.format.DateTimeFormatter

class DailyPlanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val formatter = DateTimeFormatter.ofPattern("EEEE, dd.MM.")
    private val datum: TextView = itemView.findViewById(R.id.datum)
    private val dishes: LinearLayout = itemView.findViewById(R.id.dishes)

    fun bindData(dailyPlan: DailyPlan, table: Table) {
        datum.text = formatter.format(dailyPlan.date)
        dishes.removeAllViews()
        for (dish in dailyPlan) {
            val textView = TextView(dishes.context)
            val price: String = if (table.contains(dish.category))
                table.getStudentPrice(dish.category)
            else
                "¯\\_(ツ)_/¯"

            textView.text = "${dish.name}, $price"
            dishes.addView(textView)
        }
    }
}
