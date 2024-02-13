package bitcamp.myapp.handler.member;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.DBConnectionPool;
import bitcamp.util.Prompt;

import java.sql.Connection;

public class MemberAddHandler extends AbstractMenuHandler {

  private MemberDao memberDao;
  private DBConnectionPool connectionPool;

  public MemberAddHandler(DBConnectionPool connectionPool, MemberDao memberDao) {
    this.connectionPool = connectionPool;
    this.memberDao = memberDao;
  }

  @Override
  protected void action(Prompt prompt) {
    Connection con = null;
    try {
      con = connectionPool.getConnection();
      Member member = new Member();
      member.setEmail(prompt.input("이메일? "));
      member.setName(prompt.input("이름? "));
      member.setPassword(prompt.input("암호? "));

      this.memberDao.add(member);
    } catch (Exception e) {
      prompt.println("멤버 등록 오류!");
    } finally {
      connectionPool.returnConnection(con);
    }
  }
}
