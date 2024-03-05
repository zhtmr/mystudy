package bitcamp.myapp.controller;

import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BoardController {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;
  private String uploadDir;


  public BoardController(BoardDao boardDao, AttachedFileDao fileDao,
      TransactionManager txManager, String uploadDir) {
    this.boardDao = boardDao;
    this.fileDao = fileDao;
    this.txManager = txManager;
    this.uploadDir = uploadDir;
  }



  @RequestMapping("/board/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

  @RequestMapping("/board/list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String title = "";
    int category = Integer.parseInt(request.getParameter("category"));

    title = category == 1 ? "게시글" : "가입인사";
    List<Board> list = boardDao.findAll(category);

    request.setAttribute("list", list);
    request.setAttribute("category", category);
    request.setAttribute("title", title);
    return "/board/list.jsp";
  }

  @RequestMapping("/board/view")
  public String view(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String title = "";
    int category = Integer.parseInt(request.getParameter("category"));
    title = category == 1 ? "게시글" : "가입인사";
    int no = Integer.parseInt(request.getParameter("no"));
    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    List<AttachedFile> files = fileDao.findAllByBoardNo(no);

    request.setAttribute("category", category);
    request.setAttribute("title", title);
    request.setAttribute("board", board);
    request.setAttribute("files", files);

    return "/board/view.jsp";
  }


  @RequestMapping("/board/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int category = Integer.parseInt(request.getParameter("category"));

    try {
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
      return "redirect:list?category=" + category;

    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      throw e;
    }
  }

  @RequestMapping("/board/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int category = Integer.parseInt(request.getParameter("category"));
    try {

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인하시기 바랍니다.");
      }

      int no = Integer.parseInt(request.getParameter("no"));
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
  public String boardFileDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
