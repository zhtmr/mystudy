package bitcamp.util;

public class LinkedList<E> {

  private Node<E> last;
  private Node<E> first;
  private int size;

  public void add(E value) {
    Node<E> node = new Node<>();
    node.value = value;

    if (last == null) {
      first = last = node;
    } else {
      last.next = node;
      last = node;
    }
    size++;
  }

  public E[] toArray() {
    Object[] arr = new Object[size];
    int index = 0;
    Node<E> node = first;
    while (node != null) {
      arr[index++] = node.value;
      node = node.next;
    }
    return (E[]) arr;
  }

  public E get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    int cursor = 0;
    Node<E> node = first;
    while (cursor++ < index) {
      node = node.next;
    }

    return node.value;
  }

  public E set(int index, E value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    int cursor = 0;
    Node<E> node = first;
    while (cursor++ < index) {
      node = node.next;
    }

    E oldValue = node.value;
    node.value = value;
    return oldValue;
  }

  public void add(int index, E value) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    Node<E> node = new Node<>();
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
      Node<E> prev = first;
      while (++cursor < index) {
        prev = prev.next;
      }
      node.next = prev.next;
      prev.next = node;
    }
    size++;
  }

  public E remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("무효한 인덱스입니다.");
    }

    E old;

    if (size == 1) {
      old = first.value;
      first = last = null;

    } else if (index == 0) {
      old = first.value;
      first = first.next;

    } else {
      int cursor = 0;
      Node<E> prev = first;
      while (++cursor < index) {
        prev = prev.next;
      }
      old = prev.next.value;
      prev.next = prev.next.next;

      if (index == size - 1) {
        last = prev;
      }
    }

    size--;
    return old;
  }

  public boolean remove(E value) {
    Node<E> prevNode = null;
    Node<E> node = first;

    while (node != null) {
      if (node.value.equals(value)) {
        break;
      }
      prevNode = node;
      node = node.next;
    }

    if (node == null) {
      return false;
    }

    if (node == first) {
      first = first.next;
      if (first == null) {
        last = null;
      }
    } else {
      prevNode.next = node.next;
    }

    size--;
    return true;
  }

  public int size() {
    return size;
  }

  private static class Node<E> {

    E value;
    Node<E> next;
  }


}
