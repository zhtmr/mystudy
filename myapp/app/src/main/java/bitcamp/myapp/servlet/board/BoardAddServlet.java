package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;
  private String uploadDir;


  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    txManager = (TransactionManager) this.getServletContext().getAttribute("txManager");
    fileDao = (AttachedFileDao) this.getServletContext().getAttribute("fileDao");
    uploadDir = getServletContext().getRealPath("/upload/board");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int category = Integer.parseInt(req.getParameter("category"));

    String title = category == 1 ? "게시글" : "가입인사";
    req.setAttribute("category", category);
    req.setAttribute("title", title);
    req.getRequestDispatcher("/board/form.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String title = "";
    try {
      int category = Integer.parseInt(req.getParameter("category"));
      title = category == 1 ? "게시글" : "가입인사";

      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인을 해주세요.");
      }

      Board board = new Board();
      board.setCategory(category);
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));
      board.setWriter(loginUser);

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      if (category == 1) {
        Collection<Part> parts = req.getParts();
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
      resp.sendRedirect("/board/list?category=" + category);
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      req.setAttribute("message", String.format("%s 게시글 입력 중 오류 발생!", title));
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }
  }
}
