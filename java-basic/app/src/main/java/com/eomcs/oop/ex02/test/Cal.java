package com.eomcs.oop.ex02.test;

public class Cal {
  int result = 0;
  
  void plus(int value) {
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
}
