package bitcamp.myapp;

import bitcamp.myapp.menu.MainMenu;
import bitcamp.util.Prompt;

public class AppSample {

  public static void main(String[] args) {
    Prompt prompt = new Prompt(System.in);

    new MainMenu(prompt).execute();
    prompt.close();
  }

}
