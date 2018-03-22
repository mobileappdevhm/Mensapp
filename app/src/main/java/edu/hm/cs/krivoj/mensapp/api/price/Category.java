package edu.hm.cs.krivoj.mensapp.api.price;

import java.io.Serializable;

public class Category implements Serializable {

  private String student;
  private String employee;
  private String guest;

  public Category(String student, String employee, String guest) {
    this.student = student;
    this.employee = employee;
    this.guest = guest;
  }

  public String getStudent() {
    return student;
  }

  public String getEmployee() {
    return employee;
  }

  public String getGuest() {
    return guest;
  }
}
