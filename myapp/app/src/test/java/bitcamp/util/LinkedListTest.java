package bitcamp.util;

import org.junit.jupiter.api.Test;

class LinkedListTest {

  @Test
  void add() {
  }

  @Test
  void toArray() {
  }

  @Test
  void get() {
  }

  @Test
  void set() {
  }

  @Test
  void testAdd() {
  }

  @Test
  void remove() {
    LinkedList<String> list = new LinkedList<>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    list.add("ddd");

    System.out.println(list.remove("xxx"));
    System.out.println(list.remove("ccc"));
    System.out.println(list.remove("ddd"));
    System.out.println(list.remove("aaa"));
    System.out.println(list.remove("bbb"));
    list.add("xxx");
    list.add("yyy");
    list.add("zzz");

    String[] array = list.toArray(new String[0]);
    for (Object val : array) {
      System.out.printf("%s, ", val);
    }
    System.out.println();
  }

  @Test
  void testRemove() {
  }

  @Test
  void testToArray() {
    ArrayList<String> list = new ArrayList<>();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    list.add("ddd");

    System.out.println(list.remove("xxx"));
    System.out.println(list.remove("ccc"));
    System.out.println(list.remove("ddd"));
    System.out.println(list.remove("aaa"));
    System.out.println(list.remove("bbb"));
    list.add("xxx");
    list.add("yyy");
    list.add("zzz");

    String[] array = list.toArray(new String[0]);
    for (Object val : array) {
      System.out.printf("%s, ", val);
    }
    System.out.println();
  }

  @Test
  void size() {
  }
}
