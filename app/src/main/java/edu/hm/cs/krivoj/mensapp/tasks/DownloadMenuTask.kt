package edu.hm.cs.krivoj.mensapp.tasks

import android.os.AsyncTask

import edu.hm.cs.krivoj.mensapp.api.Canteen
import edu.hm.cs.krivoj.mensapp.api.Downloader
import edu.hm.cs.krivoj.mensapp.api.menu.Menu

class DownloadMenuTask(private val downloadListener: DownloadListener) : AsyncTask<Canteen, Void, Menu>() {
    private var throwable: Throwable? = null
    private var canteen: Canteen? = null

    override fun doInBackground(vararg canteens: Canteen): Menu? {
        if (canteens.size != 1) {
            throw IllegalArgumentException("Expecting exactly one canteen.")
        }
        canteen = canteens[0]
        return try {
            Downloader.getMenu(canteen!!)
        } catch (e: Exception) {
            throwable = e
            null
        }
    }

    override fun onPostExecute(menu: Menu?) = if (menu == null)
        downloadListener.onMenuDownloadFailure(throwable)
    else downloadListener.onMenuDownloadSuccess(menu, canteen!!)

    interface DownloadListener {
        fun onMenuDownloadSuccess(menu: Menu, canteen: Canteen)
        fun onMenuDownloadFailure(throwable: Throwable?)
    }
}
