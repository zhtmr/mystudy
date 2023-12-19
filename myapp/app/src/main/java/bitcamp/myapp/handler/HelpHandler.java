package bitcamp.myapp.handler;

import bitcamp.menu.AbstractMenuHandler;

public class HelpHandler extends AbstractMenuHandler {

  @Override
  public void action() {
    System.out.println("도움말입니다.");
  }
}
