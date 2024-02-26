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
import java.sql.Date;

@WebServlet("/assignment/add")
public class AssignmentAddServlet extends HttpServlet {

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
    req.setCharacterEncoding("UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");

    req.getRequestDispatcher("/header").include(req, resp);
    out.println("<h1>과제</h1>");

    out.println("<form action='/assignment/add' method='post'>");
    out.println("<div>");
    out.println("제목: <input type='text' name='title'>");
    out.println("</div>");
    out.println("<div>");
    out.println("<label for='content'>내용: </label><textarea name='content'></textarea>");
    out.println("</div>");
    out.println("<div>");
    out.println("제출일: <input name='deadline' type='date' >");
    out.println("</div>");
    out.println("<div>");
    out.println("<button>등록</button>");
    out.println("</div>");
    out.println("</form>");
    req.getRequestDispatcher("/footer").include(req, resp);
    out.println("</body>");
    out.println("</html>");

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      Assignment assignment = new Assignment();
      assignment.setTitle(req.getParameter("title"));
      assignment.setContent(req.getParameter("content"));
      assignment.setDeadline(Date.valueOf(req.getParameter("deadline")));

      assignmentDao.add(assignment);

      resp.sendRedirect("/assignment/list");
      return;
    } catch (Exception e) {
      req.setAttribute("message", "과제 입력 중 오류 발생!");
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
    req.getRequestDispatcher("/footer").include(req, resp);
  }
}
