// 아규먼트에 람다(lambda) 활용
package com.eomcs.oop.ex12;

public class Exam0310 {

  static interface Player {
    void play();
  }

  static void testPlayer(Player p) {
    p.play();
  }

  public static void main(String[] args) {
    // 로컬 클래스
    class MyPlayer implements Player {
      @Override
      public void play() {
        System.out.println("실행!2");
      }
    }
    
    // 익명클래스
    Player p2 = new Player() {
      @Override
      public void play() {
        System.out.println("실행!");
      }
    };
    
    class MyPlayer2 implements Player {
      @Override
      public void play() {
        System.out.println("실행!");
      }
    };
    
    // 익명클래스
    testPlayer(new Player() {
      @Override
      public void play() {
        System.out.println("실행!1");
      }
    });
    
    // 람다
    Player p4 = () -> System.out.println("실행!");
    
//    testPlayer(new Player() {
//      public void play() {
//        System.out.println("ttttt");
//      }
//    }); 

    testPlayer(new MyPlayer());

  }
}


