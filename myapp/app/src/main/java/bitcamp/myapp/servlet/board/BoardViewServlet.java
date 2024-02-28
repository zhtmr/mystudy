package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/view")
public class BoardViewServlet extends HttpServlet {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;


  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) getServletContext().getAttribute("boardDao");
    fileDao = (AttachedFileDao) getServletContext().getAttribute("fileDao");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String title = "";
    try {
      int category = Integer.parseInt(req.getParameter("category"));
      title = category == 1 ? "게시글" : "가입인사";
      int no = Integer.parseInt(req.getParameter("no"));
      Board board = boardDao.findBy(no);
      if (board == null) {
        throw new Exception("번호가 유효하지 않습니다.");
      }

      List<AttachedFile> files = fileDao.findAllByBoardNo(no);

      req.setAttribute("category", category);
      req.setAttribute("board", board);
      req.setAttribute("files", files);

      req.getRequestDispatcher("/board/view.jsp").forward(req, resp);
    } catch (Exception e) {
      req.setAttribute("message", String.format("%s 조회 오류 발생!", title));
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }
  }
}
