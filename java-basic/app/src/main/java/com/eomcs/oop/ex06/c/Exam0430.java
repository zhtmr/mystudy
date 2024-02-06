// 오버라이딩(overriding) - 준비
package com.eomcs.oop.ex06.c;



public class Exam0430 {

  static class A {
    String name = "A";
    boolean working = true;

    void print() {
      System.out.println("A.print():");
      System.out.printf("  => this.name(%s)\n", this.name);
      System.out.printf("  => this.working(%s)\n", this.working);
    }
    
    void m1() {
    	System.out.println("A.m1()");
    }
  }


  static class A2 extends A {
    int age = 20;
    String name ="A2";
   
    @Override
    void print() {
      System.out.println("A2.print():");
      System.out.printf("  => this.name(%s), super.name(%s)\n",
          this.name, super.name);
      System.out.printf("  => this.working(%s), super.working(%s)\n",
          this.working, super.working);
      System.out.printf("  => this.age(%s), super.age(컴파일 오류!) \n",
          this.age /*, super.age */);
      
      this.m1();
      super.m1();
    }
    
    @Override
    void m1() {
    	System.out.println("A2.m1()");
    }
  }


  static class A3 extends A2 {
    String name = "A3";

    @Override
    void m1() {
    	System.out.println("A3.m1()");
    }
  }


  public static void main(String[] args) {
	  A2 obj = new A3();		// A3.m1() , A.m1()
//	  A2 obj = new A2();        // A2.m1() , A.m1()
	  obj.print();
	  
	  
	  /* 필드를 가리킬때의 this 는 메소드가 위치한 곳의 필드에서 찾고, 메소드를 실행할때는 실제 인스턴스의 위치에서부터 찾는다. */
	  /*    --> 그냥 필드는 오버라이딩이 안된다고 생각하면 된다!!  이름을 중복으로 선언해서 오버라이딩이라고 착각하는걸 주의! */
  }
}
