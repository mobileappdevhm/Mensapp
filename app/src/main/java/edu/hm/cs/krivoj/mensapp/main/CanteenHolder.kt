package edu.hm.cs.krivoj.mensapp.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import edu.hm.cs.krivoj.mensapp.R
import edu.hm.cs.krivoj.mensapp.api.Canteen

/**
 * Viewholder für [CanteenAdapter].
 */
class CanteenHolder
/**
 * Erstellt Viewholder für angegebene View.
 * @param itemView View aus der Viewholder erstellt werden soll
 */
(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val canteen: TextView = itemView.findViewById(R.id.canteen)
    private val location: TextView = itemView.findViewById(R.id.location)

    /**
     * Zeigt die Daten auf der View an die in diesem Viewholder steckt.
     * @param data Anzuzeigende Canteen
     */
    fun bindData(data: Canteen) {
        canteen.text = data.name
        location.text = data.location
    }
}
