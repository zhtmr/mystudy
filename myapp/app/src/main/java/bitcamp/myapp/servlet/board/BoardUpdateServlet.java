package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {

  private BoardDao boardDao;
  private TransactionManager txManager;
  private AttachedFileDao fileDao;


  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) getServletContext().getAttribute("boardDao");
    txManager = (TransactionManager) getServletContext().getAttribute("txManager");
    fileDao = (AttachedFileDao) getServletContext().getAttribute("fileDao");
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

      int no = Integer.parseInt(req.getParameter("no"));
      Board board = boardDao.findBy(no);

      if (board == null) {
        throw new Exception("번호가 유효하지 않습니다.");
      } else if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("<p>권한이 없습니다.</p>");
      }


      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      if (category == 1) {
        String[] files = req.getParameterValues("files");
        if (files != null) {
          for (String file : files) {
            if (file.isEmpty()) {
              continue;
            }
            attachedFiles.add(new AttachedFile().filePath(file));
          }
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
      resp.sendRedirect("/board/list?category=" + category);
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      req.setAttribute("message", String.format("%s 게시글 변경 오류 발생!", title));
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
