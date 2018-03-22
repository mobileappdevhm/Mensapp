package edu.hm.cs.krivoj.mensapp.menu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;

import edu.hm.cs.krivoj.mensapp.R;
import edu.hm.cs.krivoj.mensapp.api.menu.DailyPlan;
import edu.hm.cs.krivoj.mensapp.api.menu.Dish;
import edu.hm.cs.krivoj.mensapp.api.price.Table;

public class DailyPlanHolder extends RecyclerView.ViewHolder {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd.MM.");
    private TextView datum;
    private LinearLayout dishes;

    public DailyPlanHolder(View itemView) {
        super(itemView);
        datum = itemView.findViewById(R.id.datum);
        dishes = itemView.findViewById(R.id.dishes);
    }

    // TODO stub
    public void bindData(DailyPlan dailyPlan, Table table) {
        datum.setText(formatter.format(dailyPlan.getDate()));
        dishes.removeAllViews();
        for (Dish dish : dailyPlan) {
            TextView textView = new TextView(dishes.getContext());
            String price;
            if (table.contains(dish.getCategory())) {
                price = table.getStudentPrice(dish.getCategory());
            } else {
                price = "¯\\_(ツ)_/¯";
            }
            textView.setText(dish.getName() + ", " + price);
            dishes.addView(textView);
        }
    }
}
