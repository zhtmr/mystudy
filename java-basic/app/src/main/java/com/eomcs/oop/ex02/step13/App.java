package com.eomcs.oop.ex02.step13;

//0) 클래스 사용 전: 낱개 변수 사용
//1) 성적 데이터를 저장할 메모리를 새로 정의: 사용자 정의 데이터 타입
//2) 리팩토링: 메서드 추출(extract method), static nested class
public class App {

  public static void main(String[] args) {

  
    Score s1 = new Score();

    
    s1.name = "홍길동";
    s1.kor = 100;
    s1.eng = 90;
    s1.math = 85;
    
    printScore(s1);
    

    Score s2 = new Score();
    s2.name = "임꺽정";
    s2.kor = 90;
    s2.eng = 80;
    s2.math = 75;
    
    printScore(s2);

    Score s3 = new Score();
    s3.name = "유관순";
    s3.kor = 80;
    s3.eng = 70;
    s3.math = 65;
   
    printScore(s3);
  }
  
  // 메소드 추출: 중복코드 제거하여 메소드로 정의해두면 재사용하기 쉽다.
  static void printScore(Score s) {
    s.sum = s.kor + s.eng + s.math;
    s.aver = (float) s.sum / 3;

    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", 
        s.name, s.kor, s.eng, s.math, s.sum, s.aver);
  }
}

