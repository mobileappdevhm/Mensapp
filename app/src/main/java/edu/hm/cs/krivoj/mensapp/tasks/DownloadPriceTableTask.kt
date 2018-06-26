package edu.hm.cs.krivoj.mensapp.tasks

import android.os.AsyncTask
import edu.hm.cs.krivoj.mensapp.api.Downloader
import edu.hm.cs.krivoj.mensapp.api.price.Table
import java.io.IOException

/**
 * Diese AsyncTask lädt die Preistabelle herunter.
 * Es werden keine Parameter für die Ausführung verlangt und kein Fortschritt zurückgegeben.
 */
class DownloadPriceTableTask
/**
 * Erstelle eine AsyncTask zum herunterladen der Preistabelle.
 * @param downloadListener Listener der auf Ergebnis der Task hören soll
 */
(private val downloadListener: DownloadListener) : AsyncTask<Void, Void, Table>() {
    private var throwable: Throwable? = null

    override fun doInBackground(vararg voids: Void): Table? = try {
        Downloader.priceTable
    } catch (e: IOException) {
        throwable = e
        null
    }

    override fun onPostExecute(table: Table?) = if (table != null)
            downloadListener.onPriceDownloadSuccess(table)
    else downloadListener.onPriceDownloadFailure(throwable)

    /**
     * Klassen die dieses Interface implementieren werden bei Abschluss der Task benachrichtigt.
     */
    interface DownloadListener {
        /**
         * Meldet erfolgreichen Abschluss des Tasks.
         * @param priceTable Heruntergeladene Preistabelle
         */
        fun onPriceDownloadSuccess(priceTable: Table)

        /**
         * Meldet Fehlschlag der Task.
         * @param t Fehler der beim Herunterladen aufgetreten ist
         */
        fun onPriceDownloadFailure(t: Throwable?)
    }
}
