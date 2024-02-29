package bitcamp.myapp.servlet.member;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

  private MemberDao memberDao;
  private String uploadDir;

  @Override
  public void init() throws ServletException {
    memberDao = (MemberDao) getServletContext().getAttribute("memberDao");
    uploadDir = getServletContext().getRealPath("/upload");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Member old = memberDao.findBy(no);
      if (old == null) {
        throw new Exception("회원 번호가 유효하지 않습니다.");
      }

      Member member = new Member();
      member.setNo(old.getNo());
      member.setName(req.getParameter("name"));
      member.setEmail(req.getParameter("email"));
      member.setPassword(req.getParameter("password"));
      member.setCreatedDate(old.getCreatedDate());

      Part photoPart = req.getPart("photo");
      if (photoPart.getSize() > 0) {
        String filename = UUID.randomUUID().toString();
        member.setPhoto(filename);
        photoPart.write(this.uploadDir + "/" + filename);
        new File(uploadDir + "/" + old.getPhoto()).delete();
      } else {
        member.setPhoto(old.getPhoto());
      }
      memberDao.update(member);
      req.setAttribute("viewUrl","redirect:list");
    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
