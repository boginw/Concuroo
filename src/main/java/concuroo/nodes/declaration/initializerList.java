package concuroo.nodes.declaration;

import concuroo.nodes.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class initializerList implements Node, List<initializer> {
  private List<initializer> list = new ArrayList<>();

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();
    for (initializer initializer: list) {
      sb.append(initializer.getLiteral());
      sb.append(" ");
    }
    return sb.toString();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator<initializer> iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(initializer initializer) {
    return list.add(initializer);
  }

  @Override
  public boolean remove(Object o) {
    return list.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return list.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends initializer> c) {
    return list.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends initializer> c) {
    return list.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return list.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return list.retainAll(c);
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public initializer get(int index) {
    return list.get(index);
  }

  @Override
  public initializer set(int index, initializer element) {
    return list.set(index, element);
  }

  @Override
  public void add(int index, initializer element) {
    list.add(index, element);
  }

  @Override
  public initializer remove(int index) {
    return list.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return list.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator<initializer> listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator<initializer> listIterator(int index) {
    return list.listIterator(index);
  }

  @Override
  public List<initializer> subList(int fromIndex, int toIndex) {
    return list.subList(fromIndex, toIndex);
  }
}