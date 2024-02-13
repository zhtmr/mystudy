package bitcamp.myapp.handler.member;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.DBConnectionPool;
import bitcamp.util.Prompt;

import java.sql.Connection;

public class MemberModifyHandler extends AbstractMenuHandler {

  private MemberDao memberDao;
  private DBConnectionPool connectionPool;

  public MemberModifyHandler(DBConnectionPool connectionPool, MemberDao memberDao) {
    this.connectionPool = connectionPool;
    this.memberDao = memberDao;
  }

  @Override
  protected void action(Prompt prompt) {
    Connection con = null;
    try {
      con = connectionPool.getConnection();
      int index = prompt.inputInt("번호? ");
      Member old = this.memberDao.findBy(index);
      if (old == null) {
        prompt.println("회원 번호가 유효하지 않습니다");
        return;
      }

      Member member = new Member();
      member.setNo(old.getNo());
      member.setEmail(prompt.input("이메일(%s)? ", old.getEmail()));
      member.setName(prompt.input("이름(%s)? ", old.getName()));
      member.setPassword(prompt.input("새 암호? "));
      member.setCreatedDate(old.getCreatedDate());

      this.memberDao.update(member);
    } catch (Exception e) {
      prompt.println("멤버 수정 오류!");
    } finally {
      connectionPool.returnConnection(con);
    }
  }
}
