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
import java.util.List;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

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

    out.println("<a href='/member/form.html'>새 회원</a>");
    out.println("<a href='/'>HOME</a>");

    try {
      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th>번호</th>");
      out.println("<th>이름</th>");
      out.println("<th>이메일</th>");
      out.println("<th>등록일</th>");
      out.println("</tr>");
      out.println("</thead>");
      out.println("<tbody>");

      List<Member> list = memberDao.findAll();
      for (Member member : list) {
        out.printf(
            "<tr> <td>%d</td> <td><a href='/member/view?no=%1$d'>%s</a></td> <td>%s</td> <td>%s</td> <tr>",
            member.getNo(), member.getName(), member.getEmail(), member.getCreatedDate());
      }
      out.println("</tbody>");
      out.println("</table>");

    } catch (Exception e) {
      e.printStackTrace();
      out.println("<p> 목록 오류!<p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
