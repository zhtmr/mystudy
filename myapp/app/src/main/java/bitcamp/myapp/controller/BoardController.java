package bitcamp.myapp.controller;

import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/board")
public class BoardController {
  private final Log log = LogFactory.getLog(this.getClass());

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;
  private String uploadDir;

  public BoardController(BoardDao boardDao, AttachedFileDao fileDao, TransactionManager txManager,
      ServletContext sc) {   // BoardController 는 ServletContext 를 필요로한다. --> WebApplicationInitializer 구현체를 만들어 리스터에서 ServletContext 를 등록하도록 한다.
    log.debug("BoardController 생성");
    this.boardDao = boardDao;
    this.fileDao = fileDao;
    this.txManager = txManager;
    this.uploadDir = sc.getRealPath("/upload/board");
  }

  @GetMapping("form")
  public String form(int category, Model model) throws Exception {
    String title = category == 1 ? "게시글" : "가입인사";
    model.addAttribute("title", title);
    model.addAttribute("category", category);
    return "/board/form.jsp";
  }

  @PostMapping("add")
  public String add(@RequestBody Board board, MultipartFile[] attachedFiles, HttpSession session, Model model)
      throws Exception {

    // 리다이렉트 할 경우 url 뒤에 쿼리 스트링으로 붙는다.
    model.addAttribute("category", board.getCategory());

    try {
      Member loginUser = (Member) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인을 해주세요.");
      }
      board.setWriter(loginUser);

      ArrayList<AttachedFile> files = new ArrayList<>();
      if (board.getCategory() == 1) {
        for (MultipartFile file : attachedFiles) {
          if (file.getSize() == 0) {
            continue;
          }
          String filename = UUID.randomUUID().toString();
          file.transferTo(new File(uploadDir + "/" + filename));
          files.add(new AttachedFile().filePath(filename));
        }
      }

      txManager.begin();

      boardDao.add(board);
      if (!files.isEmpty()) {
        for (AttachedFile attachedFile : files) {
          attachedFile.setBoardNo(board.getNo());
        }
        fileDao.addAll(files);
      }
      txManager.commit();
      return "redirect:list";
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      throw e;
    }
  }

  @GetMapping("list")
  public String list(int category, Model model) throws Exception {
    String title = "";

    title = category == 1 ? "게시글" : "가입인사";
    List<Board> list = boardDao.findAll(category);

    model.addAttribute("list", list);
    model.addAttribute("category", category);
    model.addAttribute("title", title);
    return "/board/list.jsp";
  }

  @GetMapping("view")
  public String view(int category, int no, Model model) throws Exception {
    String title;
    title = category == 1 ? "게시글" : "가입인사";
    Board board = boardDao.findBy(no);
    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    model.addAttribute("category", category);
    model.addAttribute("title", title);
    model.addAttribute("board", board);
    if (category == 1) {
      model.addAttribute("files", fileDao.findAllByBoardNo(no));
    }

    return "/board/view.jsp";
  }


  @PostMapping("update")
  public String update(Board board, MultipartFile[] attachedFiles, HttpSession session, Model model)
      throws Exception {

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

      ArrayList<AttachedFile> files = new ArrayList<>();
      if (board.getCategory() == 1) {
        for (MultipartFile file : attachedFiles) {
          if (file.getSize() == 0) {
            continue;
          }
          String filename = UUID.randomUUID().toString();
          file.transferTo(new File(uploadDir + "/" + filename));
          files.add(new AttachedFile().filePath(filename));
        }
      }

      txManager.begin();

      boardDao.update(board);
      if (!files.isEmpty()) {
        for (AttachedFile attachedFile : files) {
          attachedFile.setBoardNo(board.getNo());
        }
        fileDao.addAll(files);
      }

      txManager.commit();
      model.addAttribute("category", board.getCategory());
      return "redirect:list";

    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      throw e;
    }
  }

  @GetMapping("delete")
  public String delete(int category, int no, HttpSession session) throws Exception {

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

  @GetMapping("file/delete")
  public String boardFileDelete(int category, int no, HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다.");
    }

    AttachedFile file = fileDao.findByNo(no);
    if (file == null) {
      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
    }

    Member writer = boardDao.findBy(file.getBoardNo()).getWriter();
    if (writer.getNo() != loginUser.getNo()) {
      throw new Exception("권한이 없습니다.");
    }

    fileDao.delete(no);
    new File(uploadDir + "/" + file.getFilePath()).delete();
    return "redirect:../view?category=" + category + "&no=" + file.getBoardNo();
  }
}
