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

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {

  private MemberDao memberDao;


  @Override
  public void init() throws ServletException {
    memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    req.getRequestDispatcher("/header").include(req, resp);

    out.println("<h1>회원</h1>");

    out.println("<form action='/member/add' method='post'>");
    out.println("<div>");
    out.println("<label>");
    out.println("이름:");
    out.println("<input type='text' name='name'>");
    out.println("</label>");
    out.println("</div>");
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
    out.println("<div>");
    out.println("<button>등록</button>");
    out.println("</div>");
    out.println("</form>");
    req.getRequestDispatcher("/footer").include(req, resp);
    out.println("</body>");
    out.println("</html>");

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      Member member = new Member();
      member.setName(req.getParameter("name"));
      member.setEmail(req.getParameter("email"));
      member.setPassword(req.getParameter("password"));

      memberDao.add(member);
      resp.sendRedirect("list");
    } catch (Exception e) {
      req.setAttribute("message", "회원 등록 중 오류 발생!");
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
