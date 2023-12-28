package bitcamp.util;

public class Queue<E> extends LinkedList<E> {

  public void offer(E value) {
    add(value);
  }

  public E poll() {
    if (size == 0) {
      return null;
    }
    return remove(0);
  }

  public E peek() {
    if (size == 0) {
      return null;
    }
    return get(0);
  }

}
