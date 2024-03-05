package bitcamp.myapp.controller;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.util.UUID;

public class MemberController {

  private MemberDao memberDao;
  private String uploadDir;

  public MemberController(MemberDao memberDao, String uploadDir) {
    this.memberDao = memberDao;
    this.uploadDir = uploadDir;
  }

  @RequestMapping("/member/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/member/form.jsp";
    }
      Member member = new Member();
      member.setName(request.getParameter("name"));
      member.setEmail(request.getParameter("email"));
      member.setPassword(request.getParameter("password"));

      Part photoPart = request.getPart("photo");
      if (photoPart.getSize() > 0) {
        String filename = UUID.randomUUID().toString();
        member.setPhoto(filename);
        photoPart.write(this.uploadDir + "/" + filename);
      }
      memberDao.add(member);
      return "redirect:list";
  }

  @RequestMapping("/member/list")
  public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    req.setAttribute("list", memberDao.findAll());
    return "/member/list.jsp";
  }

  @RequestMapping("/member/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

  @RequestMapping("/member/view")
  public String view(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    int no = Integer.parseInt(req.getParameter("no"));
    Member member = memberDao.findBy(no);
    if (member == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }
    req.setAttribute("member", member);
    return "/member/view.jsp";
  }

  @RequestMapping("/member/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Member member = memberDao.findBy(no);
    if (member == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    memberDao.delete(no);
    String filename = member.getPhoto();
    if (filename != null) {
      new File(uploadDir + "/" + filename).delete();
    }
    return "redirect:list";
  }
}
