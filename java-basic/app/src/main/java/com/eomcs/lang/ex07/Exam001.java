package com.eomcs.lang.ex07;

import java.util.Scanner;

//# 메서드 : 사용 전
//
public class Exam001 {
  
  static void printSpaces(int spaceLen) {
    int spaceCnt = 1;
    
    while (spaceCnt <= spaceLen) {
      System.out.print(" ");
      spaceCnt++;
    }
  }
  
  static void printStars(int starLen) {
    int starCnt = 1;
    while (starCnt <= starLen) {
      System.out.print("*");
      starCnt++;
    }
  }
  
  static int propmtInt(String title) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print(title);
    int len = keyScan.nextInt();
    keyScan.close();
    return len;
  }
  
  static int computeSpaceLength(int len, int starLen) {
    return (len - starLen) / 2;
  }
  
  public static void main(String[] args) {
    int len = propmtInt("밑변의 길이? ");

    for (int starLen = 1; starLen <= len;  starLen += 2) {
      printSpaces(computeSpaceLength(len, starLen));
      printStars(starLen);
      System.out.println();
    }
  }
}