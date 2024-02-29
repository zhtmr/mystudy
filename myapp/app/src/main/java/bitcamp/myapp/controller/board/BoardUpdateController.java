package bitcamp.myapp.controller.board;

import bitcamp.myapp.controller.PageController;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class BoardUpdateController implements PageController {

  private BoardDao boardDao;
  private TransactionManager txManager;
  private AttachedFileDao fileDao;
  private String uploadDir;


  public BoardUpdateController(BoardDao boardDao, TransactionManager txManager,
      AttachedFileDao fileDao, String uploadDir) {
    this.boardDao = boardDao;
    this.txManager = txManager;
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
      throw new Exception("로그인을 해주세요.");
    }

    int no = Integer.parseInt(request.getParameter("no"));
    Board board = boardDao.findBy(no);

    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    } else if (board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("<p>권한이 없습니다.</p>");
    }


    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));

    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
    if (category == 1) {
      Collection<Part> parts = request.getParts();
      for (Part part : parts) {
        if (!part.getName().equals("files") || part.getSize() == 0) {
          continue;
        }
        String filename = UUID.randomUUID().toString();
        part.write(uploadDir + "/" + filename);
        attachedFiles.add(new AttachedFile().filePath(filename));
      }
    }

    txManager.begin();

    boardDao.update(board);
    if (!attachedFiles.isEmpty()) {
      for (AttachedFile attachedFile : attachedFiles) {
        attachedFile.setBoardNo(board.getNo());
      }
      fileDao.addAll(attachedFiles);
    }

    txManager.commit();
    try {
      txManager.rollback();
    } catch (Exception e2) {
    }
    return "redirect:list?category=" + category;
  }
}
