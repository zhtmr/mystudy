package bitcamp.myapp.servlet.assignment;

import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.vo.Assignment;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    resp.setContentType("text/html;charset=UTF-8");
    req.setCharacterEncoding("UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>부트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>과제</h1>");

    Member loginUser = (Member) req.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("로그인을 해주세요.");
      out.println("<a href='/auth/form.html'>Login</a>");
      out.println("</body>");
      out.println("</html>");
      return;
    }

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Assignment old = this.assignmentDao.findBy(no);
      if (old == null) {
        out.println("과제 번호가 유효하지 않습니다.");
        resp.setHeader("Refresh","1;url=list");
        return;
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
      return;
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (SQLException ex) {
      }
      out.println("<p>과제 수정 오류!</p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
