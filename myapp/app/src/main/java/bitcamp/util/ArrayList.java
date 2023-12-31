//package bitcamp.util;
//
//import java.util.Arrays;
//
//public class ArrayList<E> extends AbstractList<E> {
//
//  private Object[] objects = new Object[3];
//
//  public void add(E object) {
//    if (size == this.objects.length) {
//      int oldSize = this.objects.length;
//      int newSize = oldSize + (oldSize >> 1);
//
//      this.objects = Arrays.copyOf(this.objects, newSize);
//    }
//
//    this.objects[size++] = object;
//  }
//
//  public E remove(int index) {
//    if (index < 0 || index >= size) {
//      return null;
//    }
//
//    Object deleted = this.objects[index];
//
//    System.arraycopy(this.objects, index + 1, this.objects, index, size - (index + 1));
//
//    this.objects[--size] = null;
//
//    return (E) deleted;
//  }
//
//  public boolean remove(E value) {
//    for (int i = 0; i < size; i++) {
//      if (this.objects[i].equals(value)) {
//        remove(i);
//        return true;
//      }
//    }
//    return false; // 찾는 값이 없다. 배열에 없다.
//  }
//
//  public Object[] toArray() {
//    return Arrays.copyOf(this.objects, size);
//  }
//
//  public E[] toArray(E[] arr) {
//    if (arr.length >= size) {
//      System.arraycopy(this.objects, 0, arr, 0, size);
//      return arr;
//    }
//    return (E[]) Arrays.copyOf(this.objects, size, arr.getClass());
//  }
//
//  public E get(int index) {
//    if (index < 0 || index >= size) {
//      return null;
//    }
//    return (E) this.objects[index];
//  }
//
//  public E set(int index, E object) {
//    if (index < 0 || index >= size) {
//      return null;
//    }
//
//    Object old = this.objects[index];
//    this.objects[index] = object;
//
//    return (E) old;
//  }
//
//
//  /* 5) 익명 class 사용한 경우 */
//  @Override
//  public Iterator<E> iterator() {
//    // local class 는 외부 클래스의 변수를 사용할 수 없다.
//    // 인터페이스를 구현하자마자 생성해야한다.
//    return new Iterator<>() {
//
//      int cursor;
//
//      @Override
//      public boolean hasNext() {
//        return cursor >= 0 && cursor < ArrayList.this.size();
//      }
//
//      @Override
//      public E next() {
//        return ArrayList.this.get(cursor++);
//      }
//    };
//  }
//
//}
