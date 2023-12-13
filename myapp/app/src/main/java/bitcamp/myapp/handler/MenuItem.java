package bitcamp.myapp.handler;

import bitcamp.util.Prompt;

public class MenuItem implements Menu {

  String title;
  MenuHandler menuHandler;

  public MenuItem(String title, MenuHandler menuHandler) {
    this(title);
    this.menuHandler = menuHandler;
  }

  public MenuItem(String title) {
    this.title = title;
  }

  @Override
  public void execute(Prompt prompt) {
    if (this.menuHandler != null) {
      this.menuHandler.action(this);
    }
  }

  public String getTitle() {
    return title;
  }
}
