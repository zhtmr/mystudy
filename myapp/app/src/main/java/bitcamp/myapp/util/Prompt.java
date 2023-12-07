package bitcamp.myapp.util;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  Scanner keyIn;

  public Prompt(InputStream keyIn) {
    this.keyIn = new Scanner(keyIn);
  }

  public void close() {
    keyIn.close();
  }

  public String input(String title, Object... args) {
    System.out.printf(title, args);
    return keyIn.nextLine();
  }

  public int inputInt(String title, Object... args) {
    String str = input(title, args);
    return Integer.parseInt(str);
  }

  public float inputFloat(String title, Object... args) {
    String str = input(title, args);
    return Float.parseFloat(str);
  }

  public double inputDouble(String title, Object... args) {
    String str = input(title, args);
    return Double.parseDouble(str);
  }

  public boolean inputBoolean(String title, Object... args) {
    String str = input(title, args);
    return Boolean.parseBoolean(str);
  }

}
