package bitcamp.myapp.menu;

import bitcamp.myapp.util.Prompt;

public class HelpMenu implements Menu {

  String title;
  Prompt prompt;

  public HelpMenu(String title, Prompt prompt) {
    this.title = title;
    this.prompt = prompt;
  }


  @Override
  public void execute() {
    System.out.printf("[%s]\n", this.title);
    System.out.println("도움말 입니다.");
  }
}
