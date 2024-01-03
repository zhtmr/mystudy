package bitcamp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

  Queue<String> queue;

  @BeforeEach
  void setUp() {
    queue = new LinkedList<>();
  }

  @Test
  void offer() {
    queue.offer("A");
    queue.offer("B");
    queue.offer("C");

    assertEquals(3, queue.size());
    assertArrayEquals(new String[] {"A", "B", "C"}, queue.toArray());
  }

  @Test
  void poll() {
    queue.offer("A");
    queue.offer("B");
    queue.offer("C");

    assertEquals("A", queue.poll());
    assertEquals("B", queue.poll());
    assertEquals("C", queue.poll());
    assertEquals(0, queue.size());
    assertNull(queue.poll());
  }

  @Test
  void peek() {
    queue.offer("A");
    queue.offer("B");
    queue.offer("C");

    assertEquals("A", queue.peek());
    assertEquals(3, queue.size());
    assertArrayEquals(new String[] {"A", "B", "C"}, queue.toArray());
  }

  @Test
  @DisplayName("poll(): queue 가 비었으면 null 을 리턴한다.")
  public void testPollEmptyQueue() {
    assertNull(queue.poll());
  }

  @Test
  @DisplayName("peek(): queue 가 비었으면 null 을 리턴한다.")
  public void testPeekEmptyQueue() {
    assertNull(queue.peek());
  }
}
