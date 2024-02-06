package com.eomcs.lang.ex99;

import java.io.InputStream;
import java.util.Scanner;

public class Test1 {
  public static void main(String[] args) {
    InputStream in = System.in;
    Scanner keyIn = new Scanner(in);
    int nextInt = keyIn.nextInt();
    String str = keyIn.nextLine();
    
    System.out.println("========>" + nextInt);
    keyIn.close();
    
  }

}
