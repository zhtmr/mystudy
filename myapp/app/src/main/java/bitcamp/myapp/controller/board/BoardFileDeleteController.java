package bitcamp.myapp.controller.board;

import bitcamp.myapp.controller.PageController;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public class BoardFileDeleteController implements PageController {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private String uploadDir;


  public BoardFileDeleteController(BoardDao boardDao, AttachedFileDao fileDao, String uploadDir) {
    this.boardDao = boardDao;
    this.fileDao = fileDao;
    this.uploadDir = uploadDir;
  }


  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String title = "";
    int category = Integer.parseInt(request.getParameter("category"));
    title = category == 1 ? "게시글" : "가입인사";

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다.");
    }

    int fileNo = Integer.parseInt(request.getParameter("no"));

    AttachedFile file = fileDao.findByNo(fileNo);
    if (file == null) {
      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
    }

    Member writer = boardDao.findBy(file.getBoardNo()).getWriter();
    if (writer.getNo() != loginUser.getNo()) {
      throw new Exception("권한이 없습니다.");
    }

    fileDao.delete(fileNo);
    new File(uploadDir + "/" + file.getFilePath()).delete();
    return "redirect:../view?category=" + category + "&no=" + file.getBoardNo();
  }
}
