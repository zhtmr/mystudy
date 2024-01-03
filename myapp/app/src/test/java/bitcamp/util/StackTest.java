package bitcamp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StackTest {

  Stack<Integer> stack;

  @BeforeEach
  void setUp() {
    stack = new Stack<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
  }

  @Test
  @DisplayName("push(): 현재 4개 항목이 들어가있다.")
  void push() {
    assertEquals(4, stack.size());
  }

  @Test
  @DisplayName("pop(): 마지막 항목을 제거한다. 제거 후 사이즈는 줄어든다.")
  void pop() {
    // given

    // when
    int value = stack.pop();

    // then
    assertEquals(4, value);
    assertEquals(3, stack.size());
  }

  @Test
  @DisplayName("peek(): 항상 마지막 항목이 나온다.")
  void peek() {
    assertEquals(4, stack.peek());
    assertEquals(4, stack.peek());
    assertEquals(4, stack.peek());
  }

  @Test
  @DisplayName("empty(): 버어있으면 true")
  void empty() {
    assertFalse(stack.empty());
    while (!stack.empty()) {
      stack.pop();
    }
    assertTrue(stack.empty());
  }
}
