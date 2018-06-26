package edu.hm.cs.krivoj.mensapp.menu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import edu.hm.cs.krivoj.mensapp.R
import edu.hm.cs.krivoj.mensapp.api.menu.Menu
import edu.hm.cs.krivoj.mensapp.api.price.Table

class DailyPlanAdapter(private val menu: Menu?, private val table: Table) : RecyclerView.Adapter<DailyPlanHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyPlanHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dailyplan, parent, false)
        return DailyPlanHolder(view)
    }

    override fun onBindViewHolder(holder: DailyPlanHolder, position: Int) = holder.bindData(menu!![position], table)

    override fun getItemCount(): Int = menu!!.size
}
