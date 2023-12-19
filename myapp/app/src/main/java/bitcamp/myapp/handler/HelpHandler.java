package bitcamp.myapp.handler;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.menu.Menu;

public class HelpHandler extends AbstractMenuHandler {

  @Override
  public void action(Menu menu) {
    super.action(menu);
    System.out.println("도움말입니다.");
  }
}
