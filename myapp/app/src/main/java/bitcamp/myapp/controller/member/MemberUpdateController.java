package bitcamp.myapp.controller.member;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.util.UUID;

public class MemberUpdateController {

  private MemberDao memberDao;
  private String uploadDir;

  public MemberUpdateController(MemberDao memberDao, String uploadDir) {
    this.memberDao = memberDao;
    this.uploadDir = uploadDir;
  }

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));
    Member old = memberDao.findBy(no);
    if (old == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    Member member = new Member();
    member.setNo(old.getNo());
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));
    member.setCreatedDate(old.getCreatedDate());

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String filename = UUID.randomUUID().toString();
      member.setPhoto(filename);
      photoPart.write(this.uploadDir + "/" + filename);
      new File(uploadDir + "/" + old.getPhoto()).delete();
    } else {
      member.setPhoto(old.getPhoto());
    }
    memberDao.update(member);
    return "redirect:list";
  }
}