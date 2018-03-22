package edu.hm.cs.krivoj.mensapp.api.price;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Table implements Serializable {

  private Map<String, Category> map = new HashMap<>();

  public Category put(String key, Category value) {
    return map.put(key, value);
  }

  private static String cleanKey(String key) {
    return key.replaceAll("(Aktionsgericht)|(Aktionsessen)|(Biogericht)|(Bioessen)",
        "Bio-/Aktionsgericht").trim();
  }

  public boolean contains(String key) {
    return map.containsKey(cleanKey(key));
  }

  public String getStudentPrice(String key) {
    if (!contains(key)) {
      return "";
    }
    String clean = cleanKey(key);
    return map.get(clean).getStudent();
  }

  public String getEmployeePrice(String key) {
    if (!contains(key)) {
      return "";
    }
    String clean = cleanKey(key);
    return map.get(clean).getEmployee();
  }

  public String getGuestPrice(String key) {
    if (!contains(key)) {
      return "";
    }
    String clean = cleanKey(key);
    return map.get(clean).getGuest();
  }
}
