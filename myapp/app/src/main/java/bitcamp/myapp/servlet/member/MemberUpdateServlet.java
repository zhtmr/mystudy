package bitcamp.myapp.servlet.member;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    resp.setContentType("text/html;charset=UTF-8");
    req.setCharacterEncoding("UTF-8");
    PrintWriter out = resp.getWriter();


    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Member member = memberDao.findBy(no);
      if (member == null) {
        out.println("<p>회원 번호가 유효하지 않습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      member.setName(req.getParameter("name"));
      member.setEmail(req.getParameter("email"));
      member.setPassword(req.getParameter("password"));

      memberDao.update(member);
      resp.sendRedirect("list");
      return;
    } catch (Exception e) {
      out.println("<!DOCTYPE html>");
      out.println("<html lang='en'>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>부트캠프 5기</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>회원</h1>");

      out.println("<p>회원 변경 오류!</p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
