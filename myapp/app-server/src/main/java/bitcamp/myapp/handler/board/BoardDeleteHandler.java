package bitcamp.myapp.handler.board;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.util.Prompt;
import bitcamp.util.TransactionManager;

import java.sql.SQLException;

public class BoardDeleteHandler extends AbstractMenuHandler {

  private BoardDao boardDao;
  private TransactionManager txManager;
  private AttachedFileDao fileDao;

  public BoardDeleteHandler(TransactionManager txManager, BoardDao boardDao, AttachedFileDao fileDao) {
    this.txManager = txManager;
    this.boardDao = boardDao;
    this.fileDao = fileDao;
  }

  @Override
  protected void action(Prompt prompt) {
    try {
      txManager.begin();

      int no = prompt.inputInt("번호? ");
      if (fileDao.deleteAll(no) > 0) {
        if (boardDao.delete(no) == 0) {
          prompt.println("게시글 번호가 유효하지 않습니다.");
        } else {
          prompt.println("삭제했습니다.");
          txManager.commit();
        }
      }
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (SQLException ex) {
      }
      prompt.println("게시글 삭제 중 오류 발생!");
      prompt.println("다시 입력해주세요");
    }
  }
}
