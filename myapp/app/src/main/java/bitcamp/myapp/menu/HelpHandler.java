package bitcamp.myapp.menu;

import bitcamp.myapp.handler.Menu;
import bitcamp.myapp.handler.MenuHandler;
import bitcamp.util.AnsiEscape;

public class HelpHandler implements MenuHandler {

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR, menu.getTitle());

    System.out.println("도움말입니다.");
  }
}
