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
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");

    req.getRequestDispatcher("/header").include(req, res);

    out.println("<h1>로그인</h1>");

    out.println("<form action='/auth/login' method='post'>");
    out.println("<div>");
    out.println("<label>");
    out.println("이메일:");
    out.println("<input type='text' name='email'>");
    out.println("</label>");
    out.println("</div>");
    out.println("<div>");
    out.println("<label>");
    out.println("암호:");
    out.println("<input type='password' name='password'>");
    out.println("</label>");
    out.println("</div>");
    out.println("<button>로그인</button>");

    out.println("</form>");

    req.getRequestDispatcher("/footer").include(req, res);
    out.println("</body>");
    out.println("</html>");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      String email = req.getParameter("email");
      String password = req.getParameter("password");
      Member member = memberDao.findByEmailAndPassword(email, password);

      res.setContentType("text/html;charset=UTF-8");
      PrintWriter out = res.getWriter();

      out.println("<!DOCTYPE html>");
      out.println("<html lang='en'>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>부트캠프 5기</title>");
      out.println("</head>");
      out.println("<body>");
      req.getRequestDispatcher("/header").include(req, res);
      out.println("<h1>로그인</h1>");

      if (member != null) {
        req.getSession().setAttribute("loginUser", member);
        out.printf("<p>%s님 환영합니다.</p>\n", member.getName());
        res.setHeader("Refresh", "1;url=/");
      } else {
        out.println("<p>이메일 또는 암호가 맞지 않습니다.</p>");
        res.setHeader("Refresh", "1;url=/auth/login");
      }

      req.getRequestDispatcher("/footer").include(req, res);
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      req.setAttribute("message", "로그인 오류 발생!");
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, res);
    }
  }
}
