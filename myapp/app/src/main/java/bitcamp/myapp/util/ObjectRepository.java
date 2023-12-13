package bitcamp.myapp.util;


// 게시글 데이터 보관
public class ObjectRepository<E> {

  private Object[] objects = new Object[3];
  private int length = 0;


  public void add(E object) {
    if (this.length == this.objects.length) {
      int oldSize = this.objects.length;
      int newSize = oldSize + (oldSize >> 1);

      Object[] arr = new Object[newSize];
      System.arraycopy(this.objects, 0, arr, 0, oldSize);
      System.out.printf("배열 크기 증가: %d\n", newSize);
      this.objects = arr;
    }
    this.objects[this.length++] = object;
  }

  public E remove(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }

    Object deleted = this.objects[index];
    System.arraycopy(this.objects, index + 1, this.objects, index, this.length - (index + 1));

//    for (int i = index; i < (this.length - 1); i++) {
//      this.objects[i] = this.objects[i + 1];
//    }
    this.objects[--this.length] = null;
    return (E) deleted;
  }

  public E get(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    return (E) this.objects[index];
  }

  public Object[] toArray() {
    Object[] arr = new Object[this.length];
    for (int i = 0; i < this.length; i++) {
      arr[i] = this.objects[i];
    }
    return arr;
  }

  public void toArray(E[] arr) {
    for (int i = 0; i < this.length; i++) {
      arr[i] = (E) this.objects[i];
    }
  }

  public E set(int index, E object) {
    if (index < 0 || index >= this.length) {
      return null;
    }

    Object old = this.objects[index];
    this.objects[index] = object;
    return (E) old;
  }


  public int size() {
    return this.length;
  }


}
