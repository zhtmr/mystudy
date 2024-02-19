package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.mysql.AttachedFileDaoImpl;
import bitcamp.myapp.dao.mysql.BoardDaoImpl;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.DBConnectionPool;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;

  public BoardAddServlet() {
    DBConnectionPool connectionPool = new DBConnectionPool(
        //              "jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123"
        "jdbc:mysql://127.0.0.1/studydb", "study", "Bitcamp!@#123");
    txManager = new TransactionManager(connectionPool);
    boardDao = new BoardDaoImpl(connectionPool, 1);
    fileDao = new AttachedFileDaoImpl(connectionPool);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>부트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글</h1>");

    Member loginUser = (Member) req.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("로그인을 해주세요.");
      out.println("</body>");
      out.println("</html>");
      return;
    }
    
    try {

      Board board = new Board();
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));
      board.setWriter(loginUser);

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      String[] files = req.getParameterValues("files");
      if (files != null) {
        for (String file : files) {
          if (file.isEmpty()) {
            continue;
          }
          attachedFiles.add(new AttachedFile().filePath(file));
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
      out.println("<p>게시글 등록완료</p>");
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      out.println("<p>게시글 등록 오류!</p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
