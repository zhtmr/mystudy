// 메서드 레퍼런스 - 활용예
package com.eomcs.oop.ex12;

import java.util.function.Predicate;

public class Exam0640 {


  static class My {
    
    public boolean m() {
      return true;
    }
  }
  
  public static void main(My[] args) {
    

//    Predicate<String> p1 = My::m; // 컴파일 오류
    // 1) My 의 m()는 스태틱 메소드가 아니다.
    // 2) My 의 m()는 String 파라미터를 못 받는다.
    
//    Predicate<String> p2 = new My()::test;   // 컴파일 오류
    // My의 m()는 String 파라미터를 못 받는다.
    
    Predicate<My> p3 = My::m;   // 컴파일 ok
    // 타입 파라미터의 클래스가 인스턴스 메소드의 클래스와 같다면,
    // 다음과 같이 람다식으로 변경된다.
    // => Predicate<My> p3 = value -> value.m();
    
  }

}


