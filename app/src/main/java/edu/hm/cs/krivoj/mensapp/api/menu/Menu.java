package edu.hm.cs.krivoj.mensapp.api.menu;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import edu.hm.cs.krivoj.mensapp.api.Canteen;

public class Menu implements Iterable<DailyPlan>, Serializable {

  private List<DailyPlan> dailyPlans = new ArrayList<>();
  private Canteen canteen;
  private LocalDate begin;
  private LocalDate end;

  public Menu(Canteen canteen, LocalDate begin, LocalDate end) {
    this.canteen = canteen;
    this.begin = begin;
    this.end = end;
  }

  public LocalDate getBegin() {
    return begin;
  }

  public LocalDate getEnd() {
    return end;
  }

  public int size() {
    return dailyPlans.size();
  }

  public boolean isEmpty() {
    return dailyPlans.isEmpty();
  }

  public boolean contains(Object o) {
    return dailyPlans.contains(o);
  }

  public Iterator<DailyPlan> iterator() {
    return dailyPlans.iterator();
  }

  public Object[] toArray() {
    return dailyPlans.toArray();
  }

  public <T> T[] toArray(T[] a) {
    return dailyPlans.toArray(a);
  }

  public boolean add(DailyPlan dailyPlan) {
    return dailyPlans.add(dailyPlan);
  }

  public boolean remove(Object o) {
    return dailyPlans.remove(o);
  }

  public boolean containsAll(Collection<?> c) {
    return dailyPlans.containsAll(c);
  }

  public boolean addAll(Collection<? extends DailyPlan> c) {
    return dailyPlans.addAll(c);
  }

  public boolean addAll(int index, Collection<? extends DailyPlan> c) {
    return dailyPlans.addAll(index, c);
  }

  public boolean removeAll(Collection<?> c) {
    return dailyPlans.removeAll(c);
  }

  public boolean retainAll(Collection<?> c) {
    return dailyPlans.retainAll(c);
  }

  public void replaceAll(UnaryOperator<DailyPlan> operator) {
    dailyPlans.replaceAll(operator);
  }

  public void sort(Comparator<? super DailyPlan> c) {
    dailyPlans.sort(c);
  }

  public void clear() {
    dailyPlans.clear();
  }

  public DailyPlan get(int index) {
    return dailyPlans.get(index);
  }

  public DailyPlan set(int index, DailyPlan element) {
    return dailyPlans.set(index, element);
  }

  public void add(int index, DailyPlan element) {
    dailyPlans.add(index, element);
  }

  public DailyPlan remove(int index) {
    return dailyPlans.remove(index);
  }

  public int indexOf(DailyPlan o) {
    return dailyPlans.indexOf(o);
  }

  /**
   * Returns index of plan behind specified date. Returns -1 if none found.
   * @param date Date of wished plan
   * @return Index of plan behind date
   */
  public int indexOf(LocalDate date) {
    for (DailyPlan plan : dailyPlans) {
      if (date.equals(plan.getDate())) {
        return indexOf(plan);
      }
      if (plan.getDate().isAfter(date)) {
        return indexOf(plan);
      }
    }
    return -1;
  }

  public int lastIndexOf(Object o) {
    return dailyPlans.lastIndexOf(o);
  }

  public ListIterator<DailyPlan> listIterator() {
    return dailyPlans.listIterator();
  }

  public ListIterator<DailyPlan> listIterator(int index) {
    return dailyPlans.listIterator(index);
  }

  public List<DailyPlan> subList(int fromIndex, int toIndex) {
    return dailyPlans.subList(fromIndex, toIndex);
  }

  @Override
  public Spliterator<DailyPlan> spliterator() {
    return dailyPlans.spliterator();
  }

  public boolean removeIf(Predicate<? super DailyPlan> filter) {
    return dailyPlans.removeIf(filter);
  }

  public Stream<DailyPlan> stream() {
    return dailyPlans.stream();
  }

  public Stream<DailyPlan> parallelStream() {
    return dailyPlans.parallelStream();
  }

  @Override
  public void forEach(Consumer<? super DailyPlan> action) {
    dailyPlans.forEach(action);
  }

  public Canteen getCanteen() {
    return canteen;
  }
}
