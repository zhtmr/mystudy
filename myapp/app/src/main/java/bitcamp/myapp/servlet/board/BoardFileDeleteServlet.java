package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/board/file/delete")
public class BoardFileDeleteServlet extends HttpServlet {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private String uploadDir;


  @Override
  public void init() throws ServletException {
    boardDao = (BoardDao) getServletContext().getAttribute("boardDao");
    fileDao = (AttachedFileDao) getServletContext().getAttribute("fileDao");
    uploadDir = getServletContext().getRealPath("/upload/board");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String title = "";
    try {
      int category = Integer.parseInt(req.getParameter("category"));
      title = category == 1 ? "게시글" : "가입인사";

      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인하시기 바랍니다.");
      }

      int fileNo = Integer.parseInt(req.getParameter("no"));

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
      resp.sendRedirect(req.getHeader("Referer"));

    } catch (Exception e) {
      req.setAttribute("message", String.format("%s 파일삭제 중 오류 발생!", title));
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error").forward(req, resp);
    }
  }
}
