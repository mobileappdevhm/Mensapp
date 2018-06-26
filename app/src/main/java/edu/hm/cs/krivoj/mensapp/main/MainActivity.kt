package edu.hm.cs.krivoj.mensapp.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import edu.hm.cs.krivoj.mensapp.R
import edu.hm.cs.krivoj.mensapp.api.Canteen
import edu.hm.cs.krivoj.mensapp.api.exceptions.NoDateException
import edu.hm.cs.krivoj.mensapp.api.exceptions.NoPlanException
import edu.hm.cs.krivoj.mensapp.api.menu.Menu
import edu.hm.cs.krivoj.mensapp.api.price.Table
import edu.hm.cs.krivoj.mensapp.menu.MenuActivity
import edu.hm.cs.krivoj.mensapp.tasks.DownloadCanteensTask
import edu.hm.cs.krivoj.mensapp.tasks.DownloadMenuTask
import edu.hm.cs.krivoj.mensapp.tasks.DownloadPriceTableTask
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

/**
 * MainActivity der Mensapp mit Mensaübersicht.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, DownloadCanteensTask.DownloadListener, DownloadPriceTableTask.DownloadListener, DownloadMenuTask.DownloadListener {

    private var adapter: CanteenAdapter? = null

    private var pricesDownloaded: Boolean = false
    private var canteensDownloaded: Boolean = false

    private var pricetable: Table? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Erstelle Adapter mit leerer Canteen Liste
        adapter = CanteenAdapter()
        adapter!!.setOnClickListener(this)

        recyclerViewCantines.layoutManager = LinearLayoutManager(this)
        recyclerViewCantines.adapter = adapter

        refreshCantines!!.setOnRefreshListener { this.downloadCanteensAndPrice() }

        downloadCanteensAndPrice()
    }

    override fun onClick(view: View) {
        // Bestimme angeklickte Canteen
        val pos = recyclerViewCantines.getChildLayoutPosition(view)
        val canteen = adapter!![pos]
        // Starte Download des Menus für diese Canteen
        DownloadMenuTask(this).execute(canteen)
    }

    /**
     * Setzt den Refreshstatus des SwipeRefreshLayouts.
     * Wenn entweder die Canteens oder die Prices heruntergeladen werden, soll der Ladevorgang
     * angezeigt werden.
     */
    private fun updateRefreshLayout() {
        refreshCantines.isRefreshing = !(canteensDownloaded && pricesDownloaded)
    }

    /**
     * Starte die Downloads der Canteens und Prices.
     * Diese Methode wird beim Start der App und beim refreshen aufgerufen.
     */
    fun downloadCanteensAndPrice() {
        canteensDownloaded = false
        pricesDownloaded = false
        updateRefreshLayout()

        DownloadCanteensTask(this).execute()
        DownloadPriceTableTask(this).execute()
    }

    // Canteen

    override fun onCanteensDownloadSuccess(canteens: List<Canteen>) {
        Log.d(LOG_TAG, "List of canteens downloaded successfully")
        adapter!!.setData(canteens)

        canteensDownloaded = true
        updateRefreshLayout()
    }

    override fun onCanteensDownloadFailure(t: Throwable?) {
        Log.e(LOG_TAG, "Download of cantines failed", t)
        Snackbar.make(recyclerViewCantines, "Download der Kantinenliste fehlgeschlagen.",
                Snackbar.LENGTH_SHORT).show()

        canteensDownloaded = true
        updateRefreshLayout()
    }

    // Price

    override fun onPriceDownloadSuccess(priceTable: Table) {
        Log.d(LOG_TAG, "Table of prices downloaded successfully")
        this.pricetable = priceTable

        pricesDownloaded = true
        updateRefreshLayout()
    }

    override fun onPriceDownloadFailure(t: Throwable?) {
        Log.e(LOG_TAG, "Download of prices failed", t)
        Snackbar.make(recyclerViewCantines, "Download der Preistabelle fehlgeschlagen.",
                Snackbar.LENGTH_SHORT).show()

        pricesDownloaded = true
        updateRefreshLayout()
    }

    // Menu

    override fun onMenuDownloadSuccess(menu: Menu, canteen: Canteen) {
        Log.d(LOG_TAG, "Menu downloaded successfully")

        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(MenuActivity.EXTRA_CANTEEN, canteen)
        intent.putExtra(MenuActivity.EXTRA_MENU, menu)
        intent.putExtra(MenuActivity.EXTRA_PRICETABLE, pricetable)

        startActivity(intent)
    }

    override fun onMenuDownloadFailure(e: Throwable?) {
        Log.e(LOG_TAG, "Download or parsing of menu failed", e)
        when (e) {
            is IOException -> Snackbar.make(recyclerViewCantines, "Download des Speiseplans fehlgeschlagen.",
                    Snackbar.LENGTH_SHORT).show()
            is NoPlanException, is NoDateException -> Snackbar.make(recyclerViewCantines, "Es wurde kein Speiseplan gefunden.",
                    Snackbar.LENGTH_SHORT).show()
            else -> Snackbar.make(recyclerViewCantines, "Ein unbekannter Fehler ist aufgetreten.",
                    Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val LOG_TAG = "MainActivity"
    }

}
