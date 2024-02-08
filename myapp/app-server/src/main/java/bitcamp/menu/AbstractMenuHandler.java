package bitcamp.menu;

import bitcamp.util.AnsiEscape;
import bitcamp.util.Prompt;

public abstract class AbstractMenuHandler implements MenuHandler {

  protected Menu menu;


  @Override
  public void action(Menu menu, Prompt prompt) {
    this.printMenuTitle(menu.getTitle(), prompt);
    this.menu = menu; // 서브클래스 구현 시 사용할 일이 있다면 쓸 수 있도록 보관해 둔다.

    // Menu 를 실행할 때 이 메소드가 호출되면 즉시 서브 클래스의 다음 메소드를 호출한다.
    this.action(prompt);
  }


  private void printMenuTitle(String title, Prompt prompt) {
    prompt.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR, title);
  }


  protected abstract void action(Prompt prompt);
}
