package edu.hm.cs.krivoj.mensapp.api.menu;

import java.io.Serializable;

public class Dish implements Serializable {

  private String name;
  private String category;
  private DailyPlan dailyPlan;

  public Dish(String name, String category, DailyPlan dailyPlan) {
    this.name = name;
    this.category = category;
    this.dailyPlan = dailyPlan;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }

  public DailyPlan getDailyPlan() {
    return dailyPlan;
  }
}
