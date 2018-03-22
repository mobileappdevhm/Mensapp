package edu.hm.cs.krivoj.mensapp.api;

import java.io.Serializable;

public class Canteen implements Serializable, Comparable<Canteen> {

  private String name;
  private String location;
  private String url;

  public Canteen(String name, String location, String url) {
    this.name = name;
    this.location = location;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public int compareTo(Canteen canteen) {
    return getName().compareTo(canteen.getName());
  }
}
