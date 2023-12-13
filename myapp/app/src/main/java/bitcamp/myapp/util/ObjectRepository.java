package bitcamp.myapp.util;


// 게시글 데이터 보관
public class ObjectRepository<T> {

  private Object[] objects = new Object[3];
  private int length = 0;


  public void add(T object) {
    if (this.length == this.objects.length) {
      int oldSize = this.objects.length;
      int newSize = oldSize + (oldSize >> 1);

      Object[] arr = new Object[newSize];
      for (int i = 0; i < oldSize; i++) {
        arr[i] = this.objects[i];
      }

      this.objects = arr;
    }
    this.objects[this.length++] = object;
  }

  public T remove(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }

    Object deleted = this.objects[index];

    for (int i = index; i < (this.length - 1); i++) {
      this.objects[i] = this.objects[i + 1];
    }
    this.objects[--this.length] = null;
    return (T) deleted;
  }

  public T get(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    return (T) this.objects[index];
  }

  public Object[] toArray() {
    Object[] arr = new Object[this.length];
    for (int i = 0; i < this.length; i++) {
      arr[i] = this.objects[i];
    }
    return arr;
  }

  public void toArray(T[] arr) {
    for (int i = 0; i < this.length; i++) {
      arr[i] = (T) this.objects[i];
    }
  }

  public T set(int index, T object) {
    if (index < 0 || index >= this.length) {
      return null;
    }

    Object old = this.objects[index];
    this.objects[index] = object;
    return (T) old;
  }


  public int size() {
    return this.length;
  }


}
