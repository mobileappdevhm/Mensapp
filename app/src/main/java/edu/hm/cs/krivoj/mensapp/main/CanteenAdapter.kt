package edu.hm.cs.krivoj.mensapp.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.hm.cs.krivoj.mensapp.R
import edu.hm.cs.krivoj.mensapp.api.Canteen
import java.util.*

/**
 * Adapter zur Bereitstellung von Canteen Views für RecyclerView.
 */
class CanteenAdapter : RecyclerView.Adapter<CanteenHolder>() {

    private var canteens: List<Canteen> = ArrayList()
    private var onClickListener: View.OnClickListener? = null

    /**
     * Legt den Datensatz fest, anhand dessen die Canteen Views generiert werden sollen.
     * @param canteens Liste der Canteens die angezeigt werden sollen
     */
    fun setData(canteens: List<Canteen>) {
        this.canteens = canteens
        notifyDataSetChanged()
    }

    /**
     * Setzt den Listener für Klicks auf Canteen Views.
     * @param onClickListener Listener für Klicks auf Canteen Views
     */
    fun setOnClickListener(onClickListener: View.OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanteenHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.canteen, parent, false)
        view.setOnClickListener(onClickListener)
        return CanteenHolder(view)
    }

    override fun onBindViewHolder(holder: CanteenHolder, position: Int) = holder.bindData(canteens[position])

    override fun getItemCount(): Int = canteens.size

    /**
     * Gibt die Canteen zurück, die sich an der angegebenen Position befindet.
     * @param i Index der gewünschten Canteen
     * @return Canteen die an der angegebenen Position steht
     */
    operator fun get(i: Int): Canteen = canteens[i]

}
