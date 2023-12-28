package bitcamp.util;

public class Stack<E> extends LinkedList<E> {


  public E push(E value) {
    add(value);
    return value;
  }

  public E pop() {
    return remove(size() - 1);
  }

  public E peek() {
    return get(size() - 1);
  }

  public boolean empty() {
    return size() == 0;
  }


}
