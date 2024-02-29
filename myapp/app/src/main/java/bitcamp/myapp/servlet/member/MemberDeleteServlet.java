package bitcamp.myapp.servlet.member;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {

  private MemberDao memberDao;
  private String uploadDir;

  @Override
  public void init() throws ServletException {
    memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
    uploadDir = getServletContext().getRealPath("/upload");
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

      memberDao.delete(no);
      String filename = member.getPhoto();
      if (filename != null) {
        new File(uploadDir + "/" + filename).delete();
      }
      req.setAttribute("viewUrl","redirect:list");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
