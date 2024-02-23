package bitcamp.myapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();
    out.println("<header>");
    out.println("  <img src='https://www.google.com/logos/doodles/2024/teachers-day-2024-jan-15-6753651837110419-2xa.gif' height=100px>");
    out.println("  <a href='/assignment/list'>과제</a>");
    out.println("  <a href='/board/list?category=1'>게시글</a>");
    out.println("  <a href='/member/list'>회원</a>");
    out.println("  <a href='/board/list?category=2'>가입인사</a>");
    out.println("</header>");
  }
}
