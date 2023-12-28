package bitcamp.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LinkedListTest {

  LinkedList<String> linkedList;

  @BeforeEach
  void init() {
    linkedList = new LinkedList<>();
  }

  @Test
  void add() {
    linkedList.add("A");
    linkedList.add("B");
    linkedList.add("C");

    assertEquals(3, linkedList.size());
    assertArrayEquals(new String[]{"A", "B", "C"}, linkedList.toArray());
  }

  @Test
  void toArray() {
  }

  @Test
  void get() {
    linkedList.add("A");
    linkedList.add("B");
    linkedList.add("C");

    assertEquals("B", linkedList.get(1));
  }

  @Test
  void set() {
    linkedList.add("A");
    linkedList.add("B");
    linkedList.add("C");

    assertEquals("B", linkedList.set(1, "D"));
    assertArrayEquals(new String[]{"A", "D", "C"}, linkedList.toArray());
  }

  @Test
  @DisplayName("add(int index, E value)")
  void testAddAtIndex() {
    linkedList.add("A");
    linkedList.add("B");
    linkedList.add("C");

    linkedList.add(1, "D");

    assertEquals(4, linkedList.size());
    assertArrayEquals(new String[]{"A", "D", "B", "C"}, linkedList.toArray());
  }

  @Test
  @DisplayName("remove(int index)")
  void remove() {
    linkedList.add("A");
    linkedList.add("B");
    linkedList.add("C");

    assertEquals("B", linkedList.remove(1));
    assertEquals(2, linkedList.size());
    assertArrayEquals(new String[]{"A", "C"}, linkedList.toArray());
  }

  @Test
  @DisplayName("remove(E value)")
  void removeByValue() {
    linkedList.add("A");
    linkedList.add("B");
    linkedList.add("C");

    assertTrue(linkedList.remove("B"));
    assertEquals(2, linkedList.size());
    assertArrayEquals(new String[]{"A", "C"}, linkedList.toArray());
  }

  @Test
  @DisplayName("toArray(final E[] arr): size 보다 작으면 배열 복사")
  void toArrayWithArrayArgument() {
    linkedList.add("A");
    linkedList.add("B");
    linkedList.add("C");

    String[] ret = linkedList.toArray(new String[0]);

    assertArrayEquals(new String[]{"A", "B", "C"}, ret);
  }

  @Test
  void size() {
  }
}
