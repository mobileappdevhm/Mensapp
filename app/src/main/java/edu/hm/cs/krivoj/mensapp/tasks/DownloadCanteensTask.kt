package edu.hm.cs.krivoj.mensapp.tasks

import android.os.AsyncTask
import edu.hm.cs.krivoj.mensapp.api.Canteen
import edu.hm.cs.krivoj.mensapp.api.Downloader
import java.io.IOException

/**
 * Diese AsyncTask lädt die Liste aller Canteens runter.
 * Es werden keine Parameter für die Ausführung verlangt und kein Fortschritt zurückgegeben.
 */
class DownloadCanteensTask
/**
 * Erstelle eine AsyncTask zum Runderladen der Canteens.
 * @param downloadListener Listener der auf Abschluss der Task hören soll
 */
(private val downloadListener: DownloadListener) : AsyncTask<Void, Void, List<Canteen>>() {
    private var throwable: Throwable? = null

    override fun doInBackground(vararg voids: Void): List<Canteen>? = try {
        Downloader.canteens
    } catch (e: IOException) {
        throwable = e
        null
    }

    override fun onPostExecute(canteens: List<Canteen>?) = if (canteens == null)
        downloadListener.onCanteensDownloadFailure(throwable)
    else downloadListener.onCanteensDownloadSuccess(canteens)

    /**
     * Klassen die dieses Interface implementieren werden bei Abschluss der Task benachrichtigt.
     */
    interface DownloadListener {
        /**
         * Meldet erfolgreichen Abschluss des Tasks.
         * @param canteens Heruntergeladene Canteens
         */
        fun onCanteensDownloadSuccess(canteens: List<Canteen>)

        /**
         * Meldet Fehlschlag der Task.
         * @param t Fehler der beim Herunterladen aufgetreten ist
         */
        fun onCanteensDownloadFailure(t: Throwable?)
    }
}
