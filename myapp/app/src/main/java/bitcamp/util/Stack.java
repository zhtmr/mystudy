package bitcamp.util;

public class Stack<E> extends LinkedList<E> {


  public E push(E value) {
    super.add(value);
    return value;
  }

  public E pop() {
    return super.remove(size() - 1);
  }

  public E peek() {
    return super.get(size() - 1);
  }

  public boolean empty() {
    return size() == 0;
  }


}
