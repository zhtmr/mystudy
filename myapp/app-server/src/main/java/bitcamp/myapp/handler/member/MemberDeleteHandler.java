package bitcamp.myapp.handler.member;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.util.DBConnectionPool;
import bitcamp.util.Prompt;

import java.sql.Connection;

public class MemberDeleteHandler extends AbstractMenuHandler {

  private MemberDao memberDao;
  private DBConnectionPool connectionPool;

  public MemberDeleteHandler(DBConnectionPool connectionPool, MemberDao memberDao) {
    this.connectionPool = connectionPool;
    this.memberDao = memberDao;
  }

  @Override
  protected void action(Prompt prompt) {
    Connection con = null;
    try {
      con = connectionPool.getConnection();
      int index = prompt.inputInt("번호? ");
      if (this.memberDao.delete(index) == 0) {
        prompt.println("회원 번호가 유효하지 않습니다.");
      } else {
        prompt.println("회원 삭제 완료");
      }
    } catch (Exception e) {
      prompt.println("회원 삭제 오류!");
    } finally {
      connectionPool.returnConnection(con);
    }
  }
}
