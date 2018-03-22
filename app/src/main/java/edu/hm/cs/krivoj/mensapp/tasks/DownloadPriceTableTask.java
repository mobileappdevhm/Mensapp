package edu.hm.cs.krivoj.mensapp.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.hm.cs.krivoj.mensapp.api.Downloader;
import edu.hm.cs.krivoj.mensapp.api.price.Table;

/**
 * Diese AsyncTask lädt die Preistabelle herunter.
 * Es werden keine Parameter für die Ausführung verlangt und kein Fortschritt zurückgegeben.
 */
public class DownloadPriceTableTask extends AsyncTask<Void, Void, Table> {

    private DownloadListener downloadListener;
    private Throwable throwable = null;

    /**
     * Erstelle eine AsyncTask zum herunterladen der Preistabelle.
     * @param downloadListener Listener der auf Ergebnis der Task hören soll
     */
    public DownloadPriceTableTask(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    protected Table doInBackground(Void... voids) {
        try {
            return Downloader.getPriceTable();
        } catch (IOException e) {
            throwable = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Table table) {
        if (table != null) downloadListener.onPriceDownloadSuccess(table);
        else downloadListener.onPriceDownloadFailure(throwable);
    }

    /**
     * Klassen die dieses Interface implementieren werden bei Abschluss der Task benachrichtigt.
     */
    public interface DownloadListener {
        /**
         * Meldet erfolgreichen Abschluss des Tasks.
         * @param priceTable Heruntergeladene Preistabelle
         */
        void onPriceDownloadSuccess(Table priceTable);
        /**
         * Meldet Fehlschlag der Task.
         * @param t Fehler der beim Herunterladen aufgetreten ist
         */
        void onPriceDownloadFailure(Throwable t);
    }
}
