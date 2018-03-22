package edu.hm.cs.krivoj.mensapp.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.hm.cs.krivoj.mensapp.R;
import edu.hm.cs.krivoj.mensapp.api.menu.Menu;
import edu.hm.cs.krivoj.mensapp.api.price.Table;

public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanHolder> {

    private Table table;
    private Menu menu;

    public DailyPlanAdapter(Menu menu, Table table) {
        this.menu = menu;
        this.table = table;
    }

    @Override
    public DailyPlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dailyplan, parent, false);
        return new DailyPlanHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyPlanHolder holder, int position) {
        holder.bindData(menu.get(position), table);
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }
}
