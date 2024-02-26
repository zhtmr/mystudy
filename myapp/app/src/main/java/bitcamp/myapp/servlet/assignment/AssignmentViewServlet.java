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
import java.io.PrintWriter;

@WebServlet("/assignment/view")
public class AssignmentViewServlet extends HttpServlet {

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

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Assignment assignment = assignmentDao.findBy(no);
      if (assignment == null) {
        throw new Exception("<p>과제 번호가 유효하지 않습니다.</p>");
      }
      resp.setContentType("text/html;charset=UTF-8");
      PrintWriter out = resp.getWriter();

      out.println("<!DOCTYPE html>");
      out.println("<html lang='en'>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>부트캠프 5기</title>");
      out.println("</head>");
      out.println("<body>");
      req.getRequestDispatcher("/header").include(req, resp);
      out.println("<h1>과제</h1>");

      out.println("<form action='/assignment/update' method='post'>");
      out.println("<div>");
      out.printf("번호: <input readonly type='text' name='no' value=%s>\n", assignment.getNo());
      out.println("</div>");
      out.println("<div>");
      out.printf("제목: <input type='text' name='title' value=%s>\n", assignment.getTitle());
      out.println("</div>");
      out.println("<div>");
      out.printf("내용: <textarea name='content'>%s</textarea>\n", assignment.getContent());
      out.println("</div>");
      out.println("<div>");
      out.printf("마감일: <input type='date' name='deadline' value=%s>\n", assignment.getDeadline());
      out.println("</div>");
      out.println("<button>변경</button>");
      out.printf("<a href='/assignment/delete?no=%d'>삭제</a>\n", no);
      out.println("</form>");
      req.getRequestDispatcher("/footer").include(req, resp);
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      req.setAttribute("message", "과제 상세보기 중 오류 발생!");
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
