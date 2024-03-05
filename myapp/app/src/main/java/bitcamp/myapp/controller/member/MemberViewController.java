package bitcamp.myapp.controller.member;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberViewController {

  private MemberDao memberDao;

  public MemberViewController(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @RequestMapping
  public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
      int no = Integer.parseInt(req.getParameter("no"));
      Member member = memberDao.findBy(no);
      if (member == null) {
        throw new Exception("회원 번호가 유효하지 않습니다.");
      }
      req.setAttribute("member", member);
      return "/member/view.jsp";
  }
}
