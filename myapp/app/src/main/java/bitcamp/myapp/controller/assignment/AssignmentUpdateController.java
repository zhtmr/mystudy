package bitcamp.myapp.controller.assignment;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class AssignmentUpdateController {

  private AssignmentDao assignmentDao;

  public AssignmentUpdateController(AssignmentDao assignmentDao) {
    this.assignmentDao = assignmentDao;
  }

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      int no = Integer.parseInt(request.getParameter("no"));
      Assignment old = this.assignmentDao.findBy(no);
      if (old == null) {
        throw new Exception("과제 번호가 유효하지 않습니다.");
      }
      Assignment assignment = new Assignment();
      assignment.setNo(old.getNo());
      assignment.setTitle(request.getParameter("title"));
      assignment.setContent(request.getParameter("content"));
      assignment.setDeadline(Date.valueOf(request.getParameter("deadline")));
      assignmentDao.update(assignment);
      return "redirect:list";
  }
}