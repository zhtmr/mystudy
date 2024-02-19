package bitcamp.myapp.servlet.auth;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.mysql.MemberDaoImpl;
import bitcamp.myapp.vo.Member;
import bitcamp.util.DBConnectionPool;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/auth/login")
public class LoginServlet extends GenericServlet {

  MemberDao memberDao;

  public LoginServlet() {
    DBConnectionPool connectionPool = new DBConnectionPool(
        //              "jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123"
        "jdbc:mysql://127.0.0.1/studydb", "study", "Bitcamp!@#123");
    this.memberDao = new MemberDaoImpl(connectionPool);
  }

  @Override
  public void service(ServletRequest servletRequest, ServletResponse servletResponse)
      throws ServletException, IOException {

    // 서블릿 컨테이너가 service()를 호출할 때 넘겨주는 값을
    // HttpServletRequest 와 HttpServletResponse 다.
    // 파라미터로 넘어온 객체를 제대로 사용하고 싶다면 원래 타입으로 형변환
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse res = (HttpServletResponse) servletResponse;

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
        out.println("<a href='/'>HOME</a>");
      } else {
        out.println("<p>이메일 또는 암호가 맞지 않습니다.</p>");
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
