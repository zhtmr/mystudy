package bitcamp.myapp.handler.member;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.util.Prompt;

public class MemberDeleteHandler extends AbstractMenuHandler {

  private MemberDao memberDao;

  public MemberDeleteHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  protected void action(Prompt prompt) {
    try {
      int index = prompt.inputInt("번호? ");
      if (this.memberDao.delete(index) == 0) {
        prompt.println("회원 번호가 유효하지 않습니다.");
      } else {
        prompt.println("회원 삭제 완료");
      }
    } catch (Exception e) {
      prompt.println("회원 삭제 오류!");
    }
  }
}
