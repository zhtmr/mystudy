package bitcamp.myapp.servlet.member;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

  private MemberDao memberDao;

  @Override
  public void init() throws ServletException {
    memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Member member = memberDao.findBy(no);
      if (member == null) {
        throw new Exception("회원 번호가 유효하지 않습니다.");
      }

      member.setName(req.getParameter("name"));
      member.setEmail(req.getParameter("email"));
      member.setPassword(req.getParameter("password"));

      memberDao.update(member);
      resp.sendRedirect("list");
    } catch (Exception e) {
      req.setAttribute("message", "회원 변경 오류 발생!");
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
