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
import java.io.PrintWriter;
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

    int category = Integer.parseInt(req.getParameter("category"));
    String title = category == 1 ? "게시글" : "가입인사";

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>부트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    out.printf("<h1>%s</h1>\n", title);

    try {
      int no = Integer.parseInt(req.getParameter("no"));
      Board board = boardDao.findBy(no);

      if (board == null) {
        out.println("<p>번호가 유효하지 않습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        out.println("로그인을 해주세요.");
        out.println("</body>");
        out.println("</html>");
        return;
      } else if (board.getWriter().getNo() != loginUser.getNo()) {
        out.println("<p>권한이 없습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
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
      return;
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      out.println("<p> 등록 오류!</p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
