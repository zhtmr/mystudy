package bitcamp.myapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/footer")
public class FooterServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<footer>");
    out.println("  <address>비트캠프:  서울 강남구 강남대로94길 20, 삼오빌딩 5층</address>");
    out.println("  <p>&copy; 2024 비트캠프 네이버클라우드 데브옵스 5기</p>");
    out.println("</footer>");
  }
}
