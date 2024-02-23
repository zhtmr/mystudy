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

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>부트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원</h1>");

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Member member = memberDao.findBy(no);
      if (member == null) {
        out.println("<p>회원 번호가 유효하지 않습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      out.println("<form action='/member/update' method='post'>");
      out.println("<div>");
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

    } catch (Exception e) {
      out.println("<p>회원 상세 오류!</p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
