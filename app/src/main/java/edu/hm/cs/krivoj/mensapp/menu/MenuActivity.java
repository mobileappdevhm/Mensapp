package edu.hm.cs.krivoj.mensapp.menu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.time.LocalDate;

import edu.hm.cs.krivoj.mensapp.R;
import edu.hm.cs.krivoj.mensapp.api.menu.Menu;
import edu.hm.cs.krivoj.mensapp.api.price.Table;

public class MenuActivity extends AppCompatActivity {

    public static final String EXTRA_MENU = "edu.hm.cs.krivoj.mensapp.menu.MenuActivity.menu";
    public static final String EXTRA_PRICETABLE =
            "edu.hm.cs.krivoj.mensapp.menu.MenuActivity.pricetable";
    private static final String LOG_TAG = "MenuActivity";

    private Menu menu;

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu = (Menu) getIntent().getExtras().getSerializable(EXTRA_MENU);
        Table table = (Table) getIntent().getExtras().getSerializable(EXTRA_PRICETABLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(menu.getCanteen().getName());
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewDailyplans);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new DailyPlanAdapter(menu, table));
        scrollToToday();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> scrollToToday());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void scrollToToday() {
        Log.d(LOG_TAG, "Scrolling to today");
        LocalDate today = LocalDate.now();
        layoutManager.scrollToPositionWithOffset(menu.indexOf(today), 16);
    }
}
