package bitcamp.myapp.handler.board;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;
import bitcamp.util.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardModifyHandler extends AbstractMenuHandler {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;

  public BoardModifyHandler(TransactionManager txManager, BoardDao boardDao, AttachedFileDao fileDao) {
    this.txManager = txManager;
    this.fileDao = fileDao;
    this.boardDao = boardDao;
  }

  @Override
  protected void action(Prompt prompt) {
    try {
      txManager.begin();

      int no = prompt.inputInt("번호? ");
      Board oldBoard = boardDao.findBy(no);
      if (oldBoard == null) {
        prompt.println("게시글 번호가 유효하지 않습니다.");
        return;
      }
      List<AttachedFile> files = fileDao.findAllByBoardNo(no);
      Board board = new Board();
      board.setNo(oldBoard.getNo());
      board.setTitle(prompt.input("제목(%s)? ", oldBoard.getTitle()));
      board.setContent(prompt.input("내용(%s)? ", oldBoard.getContent()));
      board.setWriter(prompt.input("작성자(%s)? ", oldBoard.getWriter()));
      board.setCreatedDate(oldBoard.getCreatedDate());

      ArrayList<AttachedFile> list = new ArrayList<>();
      for (AttachedFile file : files) {
        file.setBoardNo(no);
        file.setFilePath(prompt.input("파일변경(%s)? ", file.getFilePath()));
        file.setNo(file.getNo());
        list.add(file);
      }

      boardDao.update(board);
      fileDao.update(list);
      prompt.println("게시글을 변경했습니다.");
      txManager.commit();
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (SQLException ex) {
      }
      prompt.println("게시글 변경 오류!");
    }
  }
}
