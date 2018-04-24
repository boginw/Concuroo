package concuroo.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TypeQualifierList implements Node, List<TypeQualifier> {

  private List<TypeQualifier> list = new ArrayList<>();

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();
    for (TypeQualifier qualifier : list) {
      sb.append(qualifier.getLiteral());
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
  public Iterator<TypeQualifier> iterator() {
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
  public boolean add(TypeQualifier TypeQualifier) {
    return list.add(TypeQualifier);
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
  public boolean addAll(Collection<? extends TypeQualifier> c) {
    return list.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends TypeQualifier> c) {
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
  public TypeQualifier get(int index) {
    return list.get(index);
  }

  @Override
  public TypeQualifier set(int index, TypeQualifier element) {
    return list.set(index, element);
  }

  @Override
  public void add(int index, TypeQualifier element) {
    list.add(index, element);
  }

  @Override
  public TypeQualifier remove(int index) {
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
  public ListIterator<TypeQualifier> listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator<TypeQualifier> listIterator(int index) {
    return list.listIterator(index);
  }

  @Override
  public List<TypeQualifier> subList(int fromIndex, int toIndex) {
    return list.subList(fromIndex, toIndex);
  }
}