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
import java.util.List;

@WebServlet("/assignment/list")
public class AssignmentListServlet extends HttpServlet {

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
    out.println("<a href='/assignment/form.html'>새 글</a>");
    out.println("<a href='/'>HOME</a>");

    try {
      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th>번호</th>");
      out.println("<th>과제</th>");
      out.println("<th>제출마감일</th>");
      out.println("</tr>");
      out.println("</thead>");
      out.println("<tbody>");

      List<Assignment> list = assignmentDao.findAll();
      for (Assignment assignment : list) {
        out.printf("<tr> <td>%s</td> <td><a href='/assignment/view?no=%1$d'>%s</a></td> <td>%s</td> </tr>", assignment.getNo(), assignment.getTitle(),
            assignment.getDeadline());
      }
    } catch (Exception e) {
      out.println("목록 오류!");
    }
  }
}
