package bitcamp.myapp.controller.board;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BoardViewController {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;


  public BoardViewController(BoardDao boardDao, AttachedFileDao fileDao) {
    this.boardDao = boardDao;
    this.fileDao = fileDao;
  }


  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String title = "";
    int category = Integer.parseInt(request.getParameter("category"));
    title = category == 1 ? "게시글" : "가입인사";
    int no = Integer.parseInt(request.getParameter("no"));
    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    List<AttachedFile> files = fileDao.findAllByBoardNo(no);

    request.setAttribute("category", category);
    request.setAttribute("title", title);
    request.setAttribute("board", board);
    request.setAttribute("files", files);

    return "/board/view.jsp";
  }
}
