// 아규먼트에 람다(lambda) 활용 II - 파라미터와 리턴 값이 있는 람다 만들기
package com.eomcs.oop.ex12;

import java.util.Comparator;

public class Exam0320 {

  @FunctionalInterface
  static interface Calculator {
    int compute(int a, int b);
  }

  static void test(Calculator c) {
    System.out.println(c.compute(100, 200));
  }

  public static void main(String[] args) {
    
    Calculator c = new Calculator() {
      @Override
      public int compute(int a, int b) {
        return a + b;
      }
    };
    
    Comparator<Integer> co = (a, b) -> a + b;
    System.out.println(co.compare(100, 2));

    // 람다
    // 파라미터와 리턴 값이 있는 메서드 구현하기
    test((a, b) -> a + b);
    
    // 익명클래스
    test(new Calculator() {
        public int compute(int a, int b) {
          return a + b;         
        }
      });
    
    // 로컬클래스
    class MyCalculator implements Calculator {
      @Override
      public int compute(int a, int b) {
        return a+b;
      }
    }
    test(new MyCalculator());

  }
}


