package bitcamp.myapp.servlet.auth;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

  MemberDao memberDao;


  @Override
  public void init() throws ServletException {
    memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    String email = req.getParameter("email");
    String password = req.getParameter("password");

    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>부트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>과제관리 시스템</h1>");
    out.println("<h2>로그인</h2>");

    try {
      Member member = memberDao.findByEmailAndPassword(email, password);
      if (member != null) {
        req.getSession().setAttribute("loginUser", member);
        out.printf("<p>%s님 환영합니다.</p>\n", member.getName());
        res.setHeader("Refresh", "1;url=/");
      } else {
        out.println("<p>이메일 또는 암호가 맞지 않습니다.</p>");
        res.setHeader("Refresh", "1;url=/auth/form.html");
      }

    } catch (Exception e) {
      e.printStackTrace();
      out.println("<p>로그인 오류!<p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }

}
