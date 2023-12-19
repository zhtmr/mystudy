package bitcamp.menu;

import bitcamp.util.AnsiEscape;

public class AbstractMenuHandler implements MenuHandler {

  @Override
  public void action(Menu menu) {
    printMenuTitle(menu.getTitle());
  }

  private void printMenuTitle(String title) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR, title);
  }
}
