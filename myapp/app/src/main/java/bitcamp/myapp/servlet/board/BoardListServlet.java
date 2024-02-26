package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
  private BoardDao boardDao;


  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) getServletContext().getAttribute("boardDao");
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String title = "";
    try {
      int category = Integer.parseInt(req.getParameter("category"));
      title = category == 1 ? "게시글" : "가입인사";

      List<Board> list = boardDao.findAll(category);

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
      out.printf("<h1>%s</h1>\n", title);

      out.printf("<a href='/board/add?category=%d'>새 글</a>\n", category);

      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th>번호</th>");
      out.println("<th>제목</th>");
      out.println("<th>작성자</th>");
      out.println("<th>등록일</th>");
      out.println("<th>첨부파일</th>");
      out.println("</tr>");
      out.println("</thead>");
      out.println("<tbody>");
      for (Board board : list) {
        out.printf(
            "<tr> <td>%d</td> <td><a href='/board/view?category=%d&no=%1$d'>%s</a></td> <td>%s</td> <td>%s</td> <td>%d</td> <tr>",
            board.getNo(), category, board.getTitle(), board.getWriter().getName(),
            board.getCreatedDate(), board.getFileCount());
      }
      out.println("</tbody>");
      out.println("</table>");
      req.getRequestDispatcher("/footer").include(req, resp);
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      req.setAttribute("message", String.format("%s 게시글 목록 오류 발생!", title));
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
