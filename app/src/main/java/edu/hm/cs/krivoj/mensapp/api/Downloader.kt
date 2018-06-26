package edu.hm.cs.krivoj.mensapp.api

import edu.hm.cs.krivoj.mensapp.api.exceptions.NoDateException
import edu.hm.cs.krivoj.mensapp.api.exceptions.NoPlanException
import edu.hm.cs.krivoj.mensapp.api.menu.DailyPlan
import edu.hm.cs.krivoj.mensapp.api.menu.Dish
import edu.hm.cs.krivoj.mensapp.api.menu.Menu
import edu.hm.cs.krivoj.mensapp.api.price.Category
import edu.hm.cs.krivoj.mensapp.api.price.Table
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

object Downloader {

    private val HOST = "http://www.studentenwerk-muenchen.de"
    private val HEADER_SPEISEPLAN_ZEITRAUM = Pattern.compile("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d bis \\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")
    private val HEADER_TAGESPLAN_DATUM = Pattern.compile("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")
    private val FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    // Ort findet sich nach erstem ', '
    val canteens: List<Canteen>
        @Throws(IOException::class)
        get() {
            val list = mutableListOf<Canteen>()
            val doc = Jsoup.connect("$HOST/mensa/speiseplan/index-de.html").get()
            val cantines = doc.getElementsByClass("js-speiseplaene-liste").first()
            for (html in cantines.html().split("<br>".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                val e = Jsoup.parseBodyFragment(html, HOST)
                val link = e.getElementsByTag("a").first()

                val name = link.ownText()
                val url = link.attr("abs:href")
                val location = html.split(", ".toRegex(), 2).toTypedArray()[1]

                list.add(Canteen(name, location, url))
            }
            list.sort()
            return list
        }

    val priceTable: Table
        @Throws(IOException::class)
        get() {
            val tabelle = Table()
            val doc = Jsoup.connect("$HOST/mensa/mensa-preise/").get()
            doc.select("table:eq(0) tr:has(td.betrag)").stream()
                    .map<Elements>{ it.children() }.forEach { tr ->
                        val name = tr[0].text().trim{ it <= ' ' }
                        val student = tr[1].text()
                        val employee = tr[2].text()
                        val guest = tr[3].text()
                        tabelle.put(name, Category(student, employee, guest))
                    }
            return tabelle
        }

    @Throws(IOException::class, NoDateException::class, NoPlanException::class)
    fun getMenu(canteen: Canteen): Menu {
        val doc = Jsoup.connect(canteen.url).get()
        val header = doc.getElementsByTag("h1").first()
        val headerMatcher = HEADER_SPEISEPLAN_ZEITRAUM.matcher(header.ownText())
        if (!headerMatcher.find()) {
            throw NoPlanException()
        }
        val dates = headerMatcher.group().split(" bis ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val begin = LocalDate.parse(dates[0], FORMATTER)
        val end = LocalDate.parse(dates[1], FORMATTER)

        val plan = Menu(begin, end)
        for (e in doc.getElementsByClass("c-schedule__item")) {
            plan.add(getDailyPlan(e))
        }
        return plan
    }

    @Throws(NoDateException::class)
    private fun getDailyPlan(element: Element): DailyPlan {
        val header = element.getElementsByClass("c-schedule__header").first().text()
        val matcher = HEADER_TAGESPLAN_DATUM.matcher(header)
        if (!matcher.find()) {
            throw NoDateException()
        }
        val date = LocalDate.parse(matcher.group(), FORMATTER)

        val dailyPlan = DailyPlan(date)
        for (e in element.getElementsByTag("li")) {
            dailyPlan.add(getDish(e))
        }
        return dailyPlan
    }

    private fun getDish(element: Element): Dish {
        val category = element.getElementsByTag("dt").first().text()
        val name = element.getElementsByClass("js-schedule-dish-description")
                .first().ownText()
        return Dish(name, category)
    }

}// Hidden default constructor
