package edu.hm.cs.krivoj.mensapp.tasks;

import android.os.AsyncTask;

import edu.hm.cs.krivoj.mensapp.api.Canteen;
import edu.hm.cs.krivoj.mensapp.api.Downloader;
import edu.hm.cs.krivoj.mensapp.api.menu.Menu;

public class DownloadMenuTask extends AsyncTask<Canteen, Void, Menu> {

    private DownloadListener downloadListener;
    private Throwable throwable;

    public DownloadMenuTask(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    protected Menu doInBackground(Canteen... canteens) {
        if (canteens.length != 1) {
            throw new IllegalArgumentException("Expecting exactly one canteen.");
        }
        try {
            return Downloader.getMenu(canteens[0]);
        } catch (Exception e) {
            throwable = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Menu menu) {
        if (menu == null) downloadListener.onMenuDownloadFailure(throwable);
        else downloadListener.onMenuDownloadSuccess(menu);
    }

    public interface DownloadListener {
        void onMenuDownloadSuccess(Menu menu);
        void onMenuDownloadFailure(Throwable throwable);
    }
}
