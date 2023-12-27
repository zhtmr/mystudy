package bitcamp.util;

public class LinkedList {

  private Node last;
  private Node first;
  private int size;

  public void add(Object value) {
    Node node = new Node();
    node.value = value;

    if (last == null) {
      first = last = node;
    } else {
      last.next = node;
      last = node;
    }
    size++;
  }

  public Object[] toArray() {
    Object[] arr = new Object[size];
    int index = 0;
    Node node = first;
    while (node != null) {
      arr[index++] = node.value;
      node = node.next;
    }
    return arr;
  }

  public Object get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    int cursor = 0;
    Node node = first;
    while (cursor++ < index) {
      node = node.next;
    }

    return node.value;
  }

  public Object set(int index, Object value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    int cursor = 0;
    Node node = first;
    while (cursor++ < index) {
      node = node.next;
    }

    Object oldValue = node.value;
    node.value = value;
    return oldValue;
  }

  public void add(int index, Object value) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    Node node = new Node();
    node.value = value;

    if (first == null) {
      first = last = node;
    } else if (index == 0) {
      node.next = first;
      first = node;
    } else if (index == size) {
      last.next = node;
      last = node;
    } else {
      int cursor = 0;
      Node prev = first;
      while (++cursor < index) {
        prev = prev.next;
      }
      node.next = prev.next;
      prev.next = node;
    }
    size++;
  }

  public Object remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    Object old = null;

    if (size == 1) {
      old = first;
      first = last = null;

    } else if (index == 0) {
      old = first;
      first = first.next;
      
    } else {
      int cursor = 0;
      Node prev = first;
      while (++cursor < index) {
        prev = prev.next;
      }
      old = prev.next;
      prev.next = prev.next.next;

      if (index == size - 1) {
        last = prev;
      }
    }

    size--;
    return old;
  }

}
