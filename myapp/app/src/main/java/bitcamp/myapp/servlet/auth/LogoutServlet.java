package bitcamp.myapp.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    req.getSession().invalidate();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>부트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>과제관리 시스템</h1>");
    out.println("<h2>로그아웃</h2>");

    out.println("<p>로그아웃 되었습니다.</p>");
    out.println("<a href='/'>HOME</a>");
    out.println("</body>");
    out.println("</html>");
  }

}
