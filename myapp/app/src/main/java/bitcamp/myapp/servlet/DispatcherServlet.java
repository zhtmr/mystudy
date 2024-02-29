package bitcamp.myapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    // url 에서 요청한 페이지 컨트롤러를 실행한다.
    System.out.println("req.getContextPath() = " + req.getContextPath());
    System.out.println("req.getPathInfo() = " + req.getPathInfo());
    req.getRequestDispatcher(req.getPathInfo()).include(req, res);

    // 페이지 컨트롤러에서 오류가 발생했으면 오류페이지로 포워딩한다.
    Throwable exception = (Throwable) req.getAttribute("exception");
    if (exception != null) {
      req.setAttribute("message", req.getPathInfo() + "실행 오류!");

      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      exception.printStackTrace(out);
      req.setAttribute("detail", writer.toString());

      req.getRequestDispatcher("/error.jsp").forward(req, res);
      return;
    }

    // 페이지 컨트롤러에서 만든 쿠키가 있다면 응답에 포함시킨다.
    List<Cookie> cookies = (List<Cookie>) req.getAttribute("cookies");
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        res.addCookie(cookie);
      }
    }

    // 페이지 컨트롤러가 알려준 jsp 로 포워딩 한다.
    String viewUrl = (String) req.getAttribute("viewUrl");
    if (viewUrl.startsWith("redirect:")) {
      res.sendRedirect(viewUrl.substring(9));
    } else {
      req.getRequestDispatcher(viewUrl).forward(req, res);
    }

  }
}
