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

@WebServlet("/assignment/delete")
public class AssignmentDeleteServlet extends HttpServlet {

  private AssignmentDao assignmentDao;
  private TransactionManager txManager;

  @Override
  public void init() throws ServletException {
    assignmentDao = (AssignmentDao) this.getServletContext().getAttribute("assignmentDao");
    txManager = (TransactionManager) this.getServletContext().getAttribute("txManager");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html;charset=UTF-8");
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
      out.println("<p>로그인하시기 바랍니다.</p>");
      out.println("<a href='/auth/form.html'>Login</a>");
      out.println("</body>");
      out.println("</html>");
      return;
    }

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Assignment assignment = assignmentDao.findBy(no);
      if (assignment == null) {
        out.println("<p>과제 번호가 유효하지 않습니다.</p>");
        resp.setHeader("Refresh", "1;url=list");
        return;
      } else  {
        assignmentDao.delete(no);
      }
      out.println("<script>");
      out.println("location.href = '/assignment/list'");
      out.println("</script>");
    } catch (Exception e) {
      out.println("<p>과제 삭제 오류!</p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
