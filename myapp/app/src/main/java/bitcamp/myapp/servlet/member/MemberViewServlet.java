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

@WebServlet("/member/view")
public class MemberViewServlet extends HttpServlet {

  private MemberDao memberDao;

  @Override
  public void init() throws ServletException {
    memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Member member = memberDao.findBy(no);
      if (member == null) {
        throw new Exception("회원 번호가 유효하지 않습니다.");
      }

      resp.setContentType("text/html;charset=UTF-8");
      PrintWriter out = resp.getWriter();

      out.println("<!DOCTYPE html>");
      out.println("<html lang='en'>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>부트캠프 5기</title>");
      out.println("</head>");
      out.println("<body>");
      req.getRequestDispatcher("/header").include(req, resp);
      out.println("<h1>회원</h1>");

      out.println("<form action='/member/update' method='post' enctype='multipart/form-data'>");
      out.println("<div>");
      out.println("<div>");
      out.printf("사진: <a href='%s'><img src='%1$s' height='80px'></a><br> <input type='file' name='photo'>\n",
          member.getPhoto() != null ? "/upload/" + member.getPhoto() : "/img/default-photo.png");
      out.println("</div>");
      out.printf("번호: <input readonly type='text' name='no' value=%s>\n", member.getNo());
      out.println("</div>");
      out.println("<div>");
      out.printf("이름: <input type='text' name='name' value=%s>\n", member.getName());
      out.println("</div>");
      out.println("<div>");
      out.printf("이메일: <input type='text' name='email' value=%s>\n", member.getEmail());
      out.println("</div>");
      out.println("<div>");
      out.println("비밀번호: <input type='password' name='password' value=%s>");
      out.println("</div>");
      out.println("<div>");
      out.println("<button>변경</button>");
      out.printf("<a href='/member/delete?no=%d'>삭제</a>\n", no);
      out.println("</div>");
      out.println("</form>");
      req.getRequestDispatcher("/footer").include(req, resp);
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      req.setAttribute("message", "회원 조회 오류 발생!");
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
