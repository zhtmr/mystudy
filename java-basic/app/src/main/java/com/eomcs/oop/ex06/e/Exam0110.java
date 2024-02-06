// final 사용법: 상속 불가!
package com.eomcs.oop.ex06.e;

// 클래스에 final 을 붙이면 이 클래스의 서브 클래스를 만들 수 없다.
// - 서브 클래스의 생성을 방지하여
//   기존 클래스를 대체하지 못하도록 할 때 사용한다.
// - 예)
//     java.lang.String
//

final class 붕어빵 {
}

// 서브클래스를 정의할 수 없다.
class 크림붕어빵 extends 붕어빵 {
	
}

//class MyString extends String {
//	
//}

// final 클래스를 상속 받을 수 없다.
public class Exam0110 // extends A
{
	public static void main(String[] args) {
		먹는다(new 붕어빵());
		먹는다(new 크림붕어빵()); // 붕어빵이 final class 가 아니라면 서브 클래스 타입을 전달가능하다.(다형성)
	}
	
	static void 먹는다(붕어빵 obj) {
		
	}
}
