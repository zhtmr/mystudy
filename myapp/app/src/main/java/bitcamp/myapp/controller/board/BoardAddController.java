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

public class BoardAddController implements PageController {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;
  private String uploadDir;


  public BoardAddController(BoardDao boardDao, AttachedFileDao fileDao,
      TransactionManager txManager, String uploadDir) {
    this.boardDao = boardDao;
    this.fileDao = fileDao;
    this.txManager = txManager;
    this.uploadDir = uploadDir;
  }



  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      int category = Integer.parseInt(request.getParameter("category"));

      String title = category == 1 ? "게시글" : "가입인사";
      request.setAttribute("category", category);
      request.setAttribute("title", title);
      return "/board/form.jsp";
    }

    int category = Integer.parseInt(request.getParameter("category"));
    try {
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인을 해주세요.");
      }

      Board board = new Board();
      board.setCategory(category);
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));
      board.setWriter(loginUser);

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

      boardDao.add(board);
      if (!attachedFiles.isEmpty()) {
        for (AttachedFile attachedFile : attachedFiles) {
          attachedFile.setBoardNo(board.getNo());
        }
        fileDao.addAll(attachedFiles);
      }
      txManager.commit();
      return "redirect:list?category=" + category;
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
     throw e;
    }
  }
}
