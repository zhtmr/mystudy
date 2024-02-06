package com.eomcs.oop.ex02.step06;

public class Calculator {
//인스턴스 변수(= non-static 변수)
  // - 작업 결과를 개별적으로 관리하고 싶을 때 인스턴스 변수로 선언한다.
  // - 인스턴스 변수는 클래스가 로딩 될 때 만들어지지 않는다.
  // - new 명령을 사용해서 만들어야 한다.
  // - 변수 선언 앞에 static이 붙지 않는다.
  
  int result = 0;
  
  //인스턴스 변수를 다룰 때는 인스턴스 메서드를 사용하는 것이 편하다!
  // 왜?
  // - 인스턴스 주소를 파라미터로 받을 필요가 없기 때문이다.
  // - 메서드를 호출할 때 앞쪽에 인스턴스 주소를 지정한다.
  void plus(int value) {
    // 메서드를 호출할 때 앞쪽에 지정한 인스턴스 주소는
    // this 라는 내장 변수에 자동으로 저장된다.
    this.result += value;
  }

  void minus(int value) {
    this.result -= value;
  }

  void multiple(int value) {
    this.result *= value;
  }

  void divide(int value) {
    this.result /= value;
  }
  
  // 인스턴스를 사용하지 않는 메서드라면 그냥 클래스 메서드로 두어라.
  static int abs(int a) {
    return a >= 0 ? a : a * -1;
  }
}
