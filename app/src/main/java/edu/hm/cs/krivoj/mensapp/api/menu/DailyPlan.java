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

public class DailyPlan implements Iterable<Dish>, Serializable {

  private LocalDate date;
  private List<Dish> dishes = new ArrayList<>();
  private Menu menu;

  public DailyPlan(Menu menu, LocalDate date) {
    this.menu = menu;
    this.date = date;
  }

  public LocalDate getDate() {
    return date;
  }

  public int size() {
    return dishes.size();
  }

  public boolean isEmpty() {
    return dishes.isEmpty();
  }

  public boolean contains(Object o) {
    return dishes.contains(o);
  }

  @Override
  public Iterator<Dish> iterator() {
    return dishes.iterator();
  }

  public Object[] toArray() {
    return dishes.toArray();
  }

  public <T> T[] toArray(T[] a) {
    return dishes.toArray(a);
  }

  public boolean add(Dish dish) {
    return dishes.add(dish);
  }

  public boolean remove(Object o) {
    return dishes.remove(o);
  }

  public boolean containsAll(Collection<?> c) {
    return dishes.containsAll(c);
  }

  public boolean addAll(Collection<? extends Dish> c) {
    return dishes.addAll(c);
  }

  public boolean addAll(int index, Collection<? extends Dish> c) {
    return dishes.addAll(index, c);
  }

  public boolean removeAll(Collection<?> c) {
    return dishes.removeAll(c);
  }

  public boolean retainAll(Collection<?> c) {
    return dishes.retainAll(c);
  }

  public void replaceAll(UnaryOperator<Dish> operator) {
    dishes.replaceAll(operator);
  }

  public void sort(Comparator<? super Dish> c) {
    dishes.sort(c);
  }

  public void clear() {
    dishes.clear();
  }

  public Dish get(int index) {
    return dishes.get(index);
  }

  public Dish set(int index, Dish element) {
    return dishes.set(index, element);
  }

  public void add(int index, Dish element) {
    dishes.add(index, element);
  }

  public Dish remove(int index) {
    return dishes.remove(index);
  }

  public int indexOf(Object o) {
    return dishes.indexOf(o);
  }

  public int lastIndexOf(Object o) {
    return dishes.lastIndexOf(o);
  }

  public ListIterator<Dish> listIterator() {
    return dishes.listIterator();
  }

  public ListIterator<Dish> listIterator(int index) {
    return dishes.listIterator(index);
  }

  public List<Dish> subList(int fromIndex, int toIndex) {
    return dishes.subList(fromIndex, toIndex);
  }

  @Override
  public Spliterator<Dish> spliterator() {
    return dishes.spliterator();
  }

  public boolean removeIf(Predicate<? super Dish> filter) {
    return dishes.removeIf(filter);
  }

  public Stream<Dish> stream() {
    return dishes.stream();
  }

  public Stream<Dish> parallelStream() {
    return dishes.parallelStream();
  }

  @Override
  public void forEach(Consumer<? super Dish> action) {
    dishes.forEach(action);
  }

  public Menu getMenu() {
    return menu;
  }
}
