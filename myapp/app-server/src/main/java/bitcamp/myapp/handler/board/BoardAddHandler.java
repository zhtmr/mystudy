package bitcamp.myapp.handler.board;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;
import bitcamp.util.TransactionManager;

import java.util.concurrent.TimeUnit;

public class BoardAddHandler extends AbstractMenuHandler {

  private BoardDao boardDao;
  TransactionManager txManager;

  public BoardAddHandler(TransactionManager txManager, BoardDao boardDao) {
    this.txManager = txManager;
    this.boardDao = boardDao;
  }

  @Override
  protected void action(Prompt prompt) {
    Board board = new Board();
    board.setTitle(prompt.input("제목? "));
    board.setContent(prompt.input("내용? "));
    board.setWriter(prompt.input("작성자? "));

    try {
      txManager.begin();

      boardDao.add(board);
      boardDao.add(board);

      TimeUnit.SECONDS.sleep(10);
      boardDao.add(board);

      txManager.commit();
    } catch (Exception e) {
      try {txManager.rollback();} catch (Exception e2) {}
    }
  }
}
