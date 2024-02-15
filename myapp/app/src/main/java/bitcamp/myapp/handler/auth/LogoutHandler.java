package bitcamp.myapp.handler.auth;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Prompt;

public class LogoutHandler extends AbstractMenuHandler {


  @Override
  protected void action(Prompt prompt) {
    Member member = (Member) prompt.getSession().getAttribute("loginUser");
    if (member != null) {
      prompt.getSession().invalidate();
      prompt.println("로그아웃 되었습니다.");
    } else {
      prompt.println("로그인을 먼저 해주세요.");
    }
  }

}
