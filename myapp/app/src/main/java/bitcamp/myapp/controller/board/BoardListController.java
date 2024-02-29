package bitcamp.myapp.controller.board;

import bitcamp.myapp.controller.PageController;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BoardListController implements PageController {
  private BoardDao boardDao;


  public BoardListController(BoardDao boardDao) {
    this.boardDao = boardDao;
  }


  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String title = "";
      int category = Integer.parseInt(request.getParameter("category"));

      title = category == 1 ? "게시글" : "가입인사";
      List<Board> list = boardDao.findAll(category);

      request.setAttribute("list", list);
      request.setAttribute("category", category);
      request.setAttribute("title", title);
      return "/board/list.jsp";
  }
}
