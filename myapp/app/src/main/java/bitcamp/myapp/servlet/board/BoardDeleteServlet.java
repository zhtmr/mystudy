package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {

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

      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인하시기 바랍니다.");
      }

      int no = Integer.parseInt(req.getParameter("no"));
      Board board = boardDao.findBy(no);
      if (board == null) {
        throw new Exception("<p> 번호가 유효하지 않습니다.</p>");
      } else if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("권한이 없습니다.");
      }

      fileDao.deleteAll(no);
      boardDao.delete(no);

      resp.sendRedirect("/board/list?category=" + category);
    } catch (Exception e) {
      req.setAttribute("message", String.format("%s 삭제 중 오류 발생!", title));
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
