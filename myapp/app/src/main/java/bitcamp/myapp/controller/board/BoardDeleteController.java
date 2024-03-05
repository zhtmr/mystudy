package bitcamp.myapp.controller.board;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class BoardDeleteController {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private String uploadDir;
  private TransactionManager txManager;

  public BoardDeleteController(BoardDao boardDao, AttachedFileDao fileDao, String uploadDir,
      TransactionManager txManager) {
    this.boardDao = boardDao;
    this.fileDao = fileDao;
    this.uploadDir = uploadDir;
    this.txManager = txManager;
  }


  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int category = Integer.parseInt(request.getParameter("category"));
    try {

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인하시기 바랍니다.");
      }

      int no = Integer.parseInt(request.getParameter("no"));
      Board board = boardDao.findBy(no);
      if (board == null) {
        throw new Exception("번호가 유효하지 않습니다.");
      } else if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("권한이 없습니다.");
      }

      List<AttachedFile> files = fileDao.findAllByBoardNo(no);
      txManager.begin();

      fileDao.deleteAll(no);
      boardDao.delete(no);

      txManager.commit();
      for (AttachedFile file : files) {
        new File(uploadDir + "/" + file.getFilePath()).delete();
      }

      return "redirect:list?category=" + category;
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (SQLException ex) {
      }
      throw e;
    }
  }
}
