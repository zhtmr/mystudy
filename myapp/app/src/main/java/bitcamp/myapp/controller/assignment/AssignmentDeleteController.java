package bitcamp.myapp.controller.assignment;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AssignmentDeleteController {

  private AssignmentDao assignmentDao;

  public AssignmentDeleteController(AssignmentDao assignmentDao) {
    this.assignmentDao = assignmentDao;
  }

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Assignment assignment = assignmentDao.findBy(no);
    if (assignment == null) {
      throw new Exception("과제 번호가 유효하지 않습니다.");
    }
    assignmentDao.delete(no);
    return "redirect:list";
  }
}
