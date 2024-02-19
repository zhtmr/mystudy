package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.mysql.AttachedFileDaoImpl;
import bitcamp.myapp.dao.mysql.BoardDaoImpl;
import bitcamp.myapp.vo.AttachedFile;
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

@WebServlet("/board/file/delete")
public class BoardFileDeleteServlet extends HttpServlet {

  private BoardDao boardDao;
  private TransactionManager txManager;
  private AttachedFileDao fileDao;

  public BoardFileDeleteServlet() {
    DBConnectionPool connectionPool = new DBConnectionPool(
        //              "jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123"
        "jdbc:mysql://127.0.0.1/studydb", "study", "Bitcamp!@#123");
    txManager = new TransactionManager(connectionPool);
    boardDao = new BoardDaoImpl(connectionPool);
    fileDao = new AttachedFileDaoImpl(connectionPool);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
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

    Member loginUser = (Member) req.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("<p>로그인하시기 바랍니다.</p>");
      out.println("</body>");
      out.println("</html>");
      return;
    }

    try {
      int fileNo = Integer.parseInt(req.getParameter("no"));

      AttachedFile file = fileDao.findByNo(fileNo);
      if (file == null) {
        out.println("<p>첨부파일 번호가 유효하지 않습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      Member writer = boardDao.findBy(file.getBoardNo()).getWriter();
      if (writer.getNo() != loginUser.getNo()) {
        out.println("<p>권한이 없습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      fileDao.delete(fileNo);
      out.println("<script>");
      out.println("  location.href = document.referrer;");
      out.println("</script>");

    } catch (Exception e) {
      out.println("<p>삭제 오류!</p>");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }
    out.println("</body>");
    out.println("</html>");

  }
}
