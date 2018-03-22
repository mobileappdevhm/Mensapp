package edu.hm.cs.krivoj.mensapp.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import edu.hm.cs.krivoj.mensapp.api.Canteen;
import edu.hm.cs.krivoj.mensapp.api.Downloader;

/**
 * Diese AsyncTask lädt die Liste aller Canteens runter.
 * Es werden keine Parameter für die Ausführung verlangt und kein Fortschritt zurückgegeben.
 */
public class DownloadCanteensTask extends AsyncTask<Void, Void, List<Canteen>> {

    private DownloadListener downloadListener;
    private Throwable throwable;

    /**
     * Erstelle eine AsyncTask zum Runderladen der Canteens.
     * @param downloadListener Listener der auf Abschluss der Task hören soll
     */
    public DownloadCanteensTask(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    protected List<Canteen> doInBackground(Void... voids) {
        try {
            return Downloader.getCanteens();
        } catch (IOException e) {
            throwable = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Canteen> canteens) {
        if (canteens == null) downloadListener.onCanteensDownloadFailure(throwable);
        else downloadListener.onCanteensDownloadSuccess(canteens);
    }

    /**
     * Klassen die dieses Interface implementieren werden bei Abschluss der Task benachrichtigt.
     */
    public interface DownloadListener {
        /**
         * Meldet erfolgreichen Abschluss des Tasks.
         * @param canteens Heruntergeladene Canteens
         */
        void onCanteensDownloadSuccess(List<Canteen> canteens);
        /**
         * Meldet Fehlschlag der Task.
         * @param t Fehler der beim Herunterladen aufgetreten ist
         */
        void onCanteensDownloadFailure(Throwable t);
    }
}
