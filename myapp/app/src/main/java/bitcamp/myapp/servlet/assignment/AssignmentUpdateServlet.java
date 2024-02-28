package bitcamp.myapp.servlet.assignment;

import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/assignment/update")
public class AssignmentUpdateServlet extends HttpServlet {

  private AssignmentDao assignmentDao;
  private TransactionManager txManager;

  @Override
  public void init() throws ServletException {
    assignmentDao = (AssignmentDao) this.getServletContext().getAttribute("assignmentDao");
    txManager = (TransactionManager) this.getServletContext().getAttribute("txManager");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Assignment old = this.assignmentDao.findBy(no);
      if (old == null) {
        throw new Exception("과제 번호가 유효하지 않습니다.");
      }
      txManager.begin();
      Assignment assignment = new Assignment();
      assignment.setNo(old.getNo());
      assignment.setTitle(req.getParameter("title"));
      assignment.setContent(req.getParameter("content"));
      assignment.setDeadline(Date.valueOf(req.getParameter("deadline")));
      assignmentDao.update(assignment);
      txManager.commit();
      resp.sendRedirect("/assignment/list");
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (SQLException ex) {
      }
      req.setAttribute("message", "과제 변경 중 오류 발생!");
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }
  }
}
