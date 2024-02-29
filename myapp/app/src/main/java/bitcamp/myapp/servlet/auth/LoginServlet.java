package bitcamp.myapp.servlet.auth;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

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

    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("email")) {
          req.setAttribute("email",cookie.getValue());
          break;
        }
      }
    }
    req.setAttribute("viewUrl","/auth/form.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      String email = req.getParameter("email");
      String password = req.getParameter("password");
      String saveEmail = req.getParameter("saveEmail");
      Cookie cookie;
      ArrayList<Cookie> cookies = new ArrayList<>();
      if (saveEmail != null) {
        cookie = new Cookie("email", email);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookies.add(cookie);
      } else {
        cookie = new Cookie("email", "");
        cookie.setMaxAge(0);
        cookies.add(cookie);
      }
      req.setAttribute("cookies", cookies);
      Member member = memberDao.findByEmailAndPassword(email, password);

      if (member != null) {
        req.getSession().setAttribute("loginUser", member);
      }

      req.setAttribute("viewUrl","/auth/login.jsp");
    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
