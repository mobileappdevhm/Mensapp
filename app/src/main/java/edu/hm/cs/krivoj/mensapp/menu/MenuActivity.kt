package edu.hm.cs.krivoj.mensapp.menu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import edu.hm.cs.krivoj.mensapp.R
import edu.hm.cs.krivoj.mensapp.api.Canteen
import edu.hm.cs.krivoj.mensapp.api.menu.Menu
import edu.hm.cs.krivoj.mensapp.api.price.Table
import kotlinx.android.synthetic.main.activity_menu.*
import java.time.LocalDate

class MenuActivity : AppCompatActivity() {

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        menu = intent.extras!!.getSerializable(EXTRA_MENU) as Menu
        val table = intent.extras!!.getSerializable(EXTRA_PRICETABLE) as Table
        val canteen = intent.extras!!.getSerializable(EXTRA_CANTEEN) as Canteen

        toolbar.title = canteen.name
        setSupportActionBar(toolbar)

        recyclerViewDailyplans.layoutManager = LinearLayoutManager(this)
        recyclerViewDailyplans.adapter = DailyPlanAdapter(menu, table)
        scrollToToday()

        fab.setOnClickListener { scrollToToday() }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun scrollToToday() {
        Log.d(LOG_TAG, "Scrolling to today")
        val today = LocalDate.now()
        val layoutManager = recyclerViewDailyplans.layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(menu!!.indexOf(today), 16)
    }

    companion object {
        const val EXTRA_MENU = "edu.hm.cs.krivoj.mensapp.menu.MenuActivity.menu"
        const val EXTRA_CANTEEN = "edu.hm.cs.krivoj.mensapp.menu.MenuActivity.canteen"
        const val EXTRA_PRICETABLE = "edu.hm.cs.krivoj.mensapp.menu.MenuActivity.pricetable"
        private const val LOG_TAG = "MenuActivity"
    }
}
