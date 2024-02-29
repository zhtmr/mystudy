package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

      req.setAttribute("list", list);
      req.setAttribute("category", category);
      req.setAttribute("title", title);
      req.getRequestDispatcher("/board/list.jsp").forward(req, resp);
    } catch (Exception e) {
      req.setAttribute("message", String.format("%s 게시글 목록 오류 발생!", title));
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }
  }
}
