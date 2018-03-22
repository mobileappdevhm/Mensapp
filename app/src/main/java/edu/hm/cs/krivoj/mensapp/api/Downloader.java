package edu.hm.cs.krivoj.mensapp.api;

import edu.hm.cs.krivoj.mensapp.api.exceptions.NoDateException;
import edu.hm.cs.krivoj.mensapp.api.exceptions.NoPlanException;
import edu.hm.cs.krivoj.mensapp.api.price.Category;
import edu.hm.cs.krivoj.mensapp.api.price.Table;
import edu.hm.cs.krivoj.mensapp.api.menu.Dish;
import edu.hm.cs.krivoj.mensapp.api.menu.Menu;
import edu.hm.cs.krivoj.mensapp.api.menu.DailyPlan;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {

  private Downloader() {
    // Hidden default constructor
  }

  private static final String HOST = "http://www.studentenwerk-muenchen.de";
  private static final Pattern HEADER_SPEISEPLAN_ZEITRAUM =
      Pattern.compile("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d bis \\d\\d\\.\\d\\d\\.\\d\\d\\d\\d");
  private static final Pattern HEADER_TAGESPLAN_DATUM =
      Pattern.compile("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d");
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  public static List<Canteen> getCanteens() throws IOException {
    List<Canteen> list = new ArrayList<>();
    Document doc = Jsoup.connect(HOST + "/mensa/speiseplan/index-de.html").get();
    Element cantines = doc.getElementsByClass("js-speiseplaene-liste").first();
    for (String html : cantines.html().split("<br>")) {
      Element e = Jsoup.parseBodyFragment(html, HOST);
      Element link = e.getElementsByTag("a").first();

      String name = link.ownText();
      String url = link.attr("abs:href");
      String location = html.split(", ", 2)[1]; // Ort findet sich nach erstem ', '

      list.add(new Canteen(name, location, url));
    }
    list.sort(Comparator.naturalOrder());
    return list;
  }

  public static Menu getMenu(Canteen canteen) throws IOException, NoDateException, NoPlanException {
    Document doc = Jsoup.connect(canteen.getUrl()).get();
    Element header = doc.getElementsByTag("h1").first();
    Matcher headerMatcher = HEADER_SPEISEPLAN_ZEITRAUM.matcher(header.ownText());
    if (!headerMatcher.find()) {
      throw new NoPlanException();
    }
    String[] dates = headerMatcher.group().split(" bis ");
    LocalDate begin = LocalDate.parse(dates[0], FORMATTER);
    LocalDate end = LocalDate.parse(dates[1], FORMATTER);

    Menu plan = new Menu(canteen, begin, end);
    for (Element e : doc.getElementsByClass("c-schedule__item")) {
      plan.add(getDailyPlan(plan, e));
    }
    return plan;
  }

  private static DailyPlan getDailyPlan(Menu menu, Element element) throws NoDateException {
    String header = element.getElementsByClass("c-schedule__header").first().text();
    Matcher matcher = HEADER_TAGESPLAN_DATUM.matcher(header);
    if (!matcher.find()) {
      throw new NoDateException();
    }
    LocalDate date = LocalDate.parse(matcher.group(), FORMATTER);

    DailyPlan dailyPlan = new DailyPlan(menu, date);
    for (Element e : element.getElementsByTag("li")) {
      dailyPlan.add(getDish(dailyPlan, e));
    }
    return dailyPlan;
  }

  private static Dish getDish(DailyPlan dailyPlan, Element element) {
    String category = element.getElementsByTag("dt").first().text();
    String name = element.getElementsByClass("js-schedule-dish-description")
        .first().ownText();
    return new Dish(name, category, dailyPlan);
  }

  public static Table getPriceTable() throws IOException {
    Table tabelle = new Table();
    Document doc = Jsoup.connect(HOST + "/mensa/mensa-preise/").get();
    doc.select("table:eq(0) tr:has(td.betrag)").stream()
        .map(Element::children).forEach(tr -> {
          String name = tr.get(0).text().trim();
          String student = tr.get(1).text();
          String employee = tr.get(2).text();
          String guest = tr.get(3).text();
          tabelle.put(name, new Category(student, employee, guest));
    });
    return tabelle;
  }

}
