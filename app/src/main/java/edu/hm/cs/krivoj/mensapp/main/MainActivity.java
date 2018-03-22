package edu.hm.cs.krivoj.mensapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;

import edu.hm.cs.krivoj.mensapp.api.Canteen;
import edu.hm.cs.krivoj.mensapp.api.exceptions.NoDateException;
import edu.hm.cs.krivoj.mensapp.api.exceptions.NoPlanException;
import edu.hm.cs.krivoj.mensapp.menu.MenuActivity;
import edu.hm.cs.krivoj.mensapp.R;
import edu.hm.cs.krivoj.mensapp.api.menu.Menu;
import edu.hm.cs.krivoj.mensapp.api.price.Table;
import edu.hm.cs.krivoj.mensapp.tasks.DownloadCanteensTask;
import edu.hm.cs.krivoj.mensapp.tasks.DownloadMenuTask;
import edu.hm.cs.krivoj.mensapp.tasks.DownloadPriceTableTask;

/**
 * MainActivity der Mensapp mit Mensaübersicht.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        DownloadCanteensTask.DownloadListener, DownloadPriceTableTask.DownloadListener,
        DownloadMenuTask.DownloadListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private CanteenAdapter adapter;
    private RecyclerView recyclerView;

    private boolean pricesDownloaded;
    private boolean canteensDownloaded;

    private Table pricetable;

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Erstelle Adapter mit leerer Canteen Liste
        adapter = new CanteenAdapter();
        adapter.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerViewCantines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.refreshCantines);
        swipeRefreshLayout.setOnRefreshListener(this::downloadCanteensAndPrice);

        downloadCanteensAndPrice();
    }

    @Override
    public void onClick(View view) {
        // Bestimme angeklickte Canteen
        int pos = recyclerView.getChildLayoutPosition(view);
        Canteen canteen = adapter.get(pos);
        // Starte Download des Menus für diese Canteen
        new DownloadMenuTask(this).execute(canteen);
    }

    /**
     * Setzt den Refreshstatus des SwipeRefreshLayouts.
     * Wenn entweder die Canteens oder die Prices heruntergeladen werden, soll der Ladevorgang
     * angezeigt werden.
     */
    private void updateRefreshLayout() {
        swipeRefreshLayout.setRefreshing(!(canteensDownloaded && pricesDownloaded));
    }

    /**
     * Starte die Downloads der Canteens und Prices.
     * Diese Methode wird beim Start der App und beim refreshen aufgerufen.
     */
    public void downloadCanteensAndPrice() {
        canteensDownloaded = false;
        pricesDownloaded = false;
        updateRefreshLayout();

        new DownloadCanteensTask(this).execute();
        new DownloadPriceTableTask(this).execute();
    }

    // Canteen

    @Override
    public void onCanteensDownloadSuccess(List<Canteen> canteens) {
        Log.d(LOG_TAG, "List of canteens downloaded successfully");
        adapter.setData(canteens);

        canteensDownloaded = true;
        updateRefreshLayout();
    }

    @Override
    public void onCanteensDownloadFailure(Throwable e) {
        Log.e(LOG_TAG, "Download of cantines failed", e);
        Snackbar.make(recyclerView, "Download der Kantinenliste fehlgeschlagen.",
                Snackbar.LENGTH_SHORT).show();

        canteensDownloaded = true;
        updateRefreshLayout();
    }

    // Price

    @Override
    public void onPriceDownloadSuccess(Table priceTable) {
        Log.d(LOG_TAG, "Table of prices downloaded successfully");
        this.pricetable = priceTable;

        pricesDownloaded = true;
        updateRefreshLayout();
    }

    @Override
    public void onPriceDownloadFailure(Throwable e) {
        Log.e(LOG_TAG, "Download of prices failed", e);
        Snackbar.make(recyclerView, "Download der Preistabelle fehlgeschlagen.",
                Snackbar.LENGTH_SHORT).show();

        pricesDownloaded = true;
        updateRefreshLayout();
    }

    // Menu

    @Override
    public void onMenuDownloadSuccess(Menu menu) {
        Log.d(LOG_TAG, "Menu downloaded successfully");

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(MenuActivity.EXTRA_MENU, menu);
        intent.putExtra(MenuActivity.EXTRA_PRICETABLE, pricetable);

        startActivity(intent);
    }

    @Override
    public void onMenuDownloadFailure(Throwable e) {
        Log.e(LOG_TAG, "Download or parsing of menu failed", e);
        if (e instanceof IOException) {
            Snackbar.make(recyclerView, "Download des Speiseplans fehlgeschlagen.",
                    Snackbar.LENGTH_SHORT).show();
        } else if (e instanceof NoPlanException || e instanceof NoDateException) {
            Snackbar.make(recyclerView, "Es wurde kein Speiseplan gefunden.",
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(recyclerView, "Ein unbekannter Fehler ist aufgetreten.",
                    Snackbar.LENGTH_SHORT).show();
        }
    }

}
