package bitcamp.myapp.controller.assignment;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AssignmentListController {

  private AssignmentDao assignmentDao;

  public AssignmentListController(AssignmentDao assignmentDao) {
    this.assignmentDao = assignmentDao;
  }

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Assignment> list = assignmentDao.findAll();
    request.setAttribute("list", list);
    return "/assignment/list.jsp";
  }
}
