package edu.hm.cs.krivoj.mensapp.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.hm.cs.krivoj.mensapp.R;
import edu.hm.cs.krivoj.mensapp.api.Canteen;

/**
 * Viewholder für {@link CanteenAdapter}.
 */
public class CanteenHolder extends RecyclerView.ViewHolder {

    private TextView canteen;
    private TextView location;

    /**
     * Erstellt Viewholder für angegebene View.
     * @param itemView View aus der Viewholder erstellt werden soll
     */
    public CanteenHolder(View itemView) {
        super(itemView);
        canteen = itemView.findViewById(R.id.canteen);
        location = itemView.findViewById(R.id.location);
    }

    /**
     * Zeigt die Daten auf der View an die in diesem Viewholder steckt.
     * @param data Anzuzeigende Canteen
     */
    public void bindData(Canteen data) {
        canteen.setText(data.getName());
        location.setText(data.getLocation());
    }
}
