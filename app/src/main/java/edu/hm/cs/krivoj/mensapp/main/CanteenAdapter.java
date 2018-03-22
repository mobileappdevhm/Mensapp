package edu.hm.cs.krivoj.mensapp.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.hm.cs.krivoj.mensapp.api.Canteen;
import edu.hm.cs.krivoj.mensapp.R;

/**
 * Adapter zur Bereitstellung von Canteen Views für RecyclerView.
 */
public class CanteenAdapter extends RecyclerView.Adapter<CanteenHolder> {

    private List<Canteen> canteens = new ArrayList<>();
    private View.OnClickListener onClickListener = null;

    /**
     * Legt den Datensatz fest, anhand dessen die Canteen Views generiert werden sollen.
     * @param canteens Liste der Canteens die angezeigt werden sollen
     */
    public void setData(List<Canteen> canteens) {
        this.canteens = canteens;
        notifyDataSetChanged();
    }

    /**
     * Setzt den Listener für Klicks auf Canteen Views.
     * @param onClickListener Listener für Klicks auf Canteen Views
     */
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public CanteenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.canteen, parent, false);
        view.setOnClickListener(onClickListener);
        return new CanteenHolder(view);
    }

    @Override
    public void onBindViewHolder(CanteenHolder holder, int position) {
        holder.bindData(canteens.get(position));
    }

    @Override
    public int getItemCount() {
        return canteens.size();
    }

    /**
     * Gibt die Canteen zurück, die sich an der angegebenen Position befindet.
     * @param i Index der gewünschten Canteen
     * @return Canteen die an der angegebenen Position steht
     */
    public Canteen get(int i) {
        return canteens.get(i);
    }
}
