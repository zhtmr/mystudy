package bitcamp.myapp.menu;

import bitcamp.myapp.util.Prompt;

// Composite pattern: leaf
public class MenuItem implements Menu {

  String title;

  public MenuItem(String title) {
    this.title = title;
  }


  @Override
  public void execute(Prompt prompt) {
    System.out.printf("[%s]\n", this.title);
  }

  @Override
  public String getTitle() {
    return this.title;
  }
}
