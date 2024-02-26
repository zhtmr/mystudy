package bitcamp.myapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/index.html")
public class HomeServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

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

    out.println("<h1>과제 관리 시스템</h1>");
    out.println("<p>환영합니다. 교육 센터 과제 관리 시스템 입니다.</p>");

    req.getRequestDispatcher("/footer").include(req, res);

    out.println("</body>");
    out.println("</html>");
  }
}
