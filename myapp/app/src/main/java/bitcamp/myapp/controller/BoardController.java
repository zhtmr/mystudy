package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class BoardController {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;
  private String uploadDir;

  public BoardController(BoardDao boardDao, AttachedFileDao fileDao, TransactionManager txManager, ServletContext sc) {
    System.out.println("BoardController 생성");
    this.boardDao = boardDao;
    this.fileDao = fileDao;
    this.txManager = txManager;
    this.uploadDir = sc.getRealPath("/upload/board");
  }

  @RequestMapping("/board/form")
  public String form(@RequestParam("category") int category, Map<String, Object> map) throws Exception {
    String title = category == 1 ? "게시글" : "가입인사";
    map.put("title", title);
    map.put("category", category);
    return "/board/form.jsp";
  }

  @RequestMapping("/board/add")
  public String add(Board board, @RequestParam("attachedFiles") Part[] files, HttpSession session,
      Map<String, Object> map) throws Exception {

    int category = board.getCategory();
    String title = category == 1 ? "게시글" : "가입인사";
    map.put("title", title);
    map.put("category", category);

    try {
      Member loginUser = (Member) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인을 해주세요.");
      }
      board.setWriter(loginUser);

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      if (category == 1) {
        for (Part file : files) {
          if (file.getSize() == 0) {
            continue;
          }
          String filename = UUID.randomUUID().toString();
          file.write(uploadDir + "/" + filename);
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

  @RequestMapping("/board/list")
  public String list(@RequestParam("category") int category, Map<String, Object> map) throws Exception {
    String title = "";

    title = category == 1 ? "게시글" : "가입인사";
    List<Board> list = boardDao.findAll(category);

    map.put("list", list);
    map.put("category", category);
    map.put("title", title);
    return "/board/list.jsp";
  }

  @RequestMapping("/board/view")
  public String view(@RequestParam("category") int category, @RequestParam("no") int no, Map<String, Object> map) throws Exception {
    String title;
    title = category == 1 ? "게시글" : "가입인사";
    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    map.put("category", category);
    map.put("title", title);
    map.put("board", board);
    if (category == 1) {
      map.put("files", fileDao.findAllByBoardNo(no));
    }

    return "/board/view.jsp";
  }


  @RequestMapping("/board/update")
  public String update(Board board, @RequestParam("attachedFiles") Part[] files, HttpSession session) throws Exception {

    try {
      Member loginUser = (Member) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인을 해주세요.");
      }

      Board old = boardDao.findBy(board.getNo());

      if (old == null) {
        throw new Exception("번호가 유효하지 않습니다.");
      } else if (old.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("권한이 없습니다.");
      }

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      if (board.getCategory() == 1) {
        for (Part file : files) {
          if (file.getSize() == 0) {
            continue;
          }
          String filename = UUID.randomUUID().toString();
          file.write(uploadDir + "/" + filename);
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
      return "redirect:list?category=" + board.getCategory();

    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      throw e;
    }
  }

  @RequestMapping("/board/delete")
  public String delete(@RequestParam("category") int category,
      @RequestParam("no") int no,
      HttpSession session) throws Exception {

    try {
      Member loginUser = (Member) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인하시기 바랍니다.");
      }

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

  @RequestMapping("/board/file/delete")
  public String boardFileDelete(
      @RequestParam("category") int category,
      @RequestParam("no") int fileNo,
      HttpSession session)
      throws Exception {
    String title = "";
    title = category == 1 ? "게시글" : "가입인사";

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다.");
    }

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
