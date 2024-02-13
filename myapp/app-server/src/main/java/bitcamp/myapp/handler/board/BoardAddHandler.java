package bitcamp.myapp.handler.board;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;
import bitcamp.util.DBConnectionPool;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class BoardAddHandler extends AbstractMenuHandler {

  private BoardDao boardDao;
  DBConnectionPool threadConnection;

  public BoardAddHandler(DBConnectionPool threadConnection, BoardDao boardDao) {
    this.threadConnection = threadConnection;
    this.boardDao = boardDao;
  }

  @Override
  protected void action(Prompt prompt) {
    Board board = new Board();
    board.setTitle(prompt.input("제목? "));
    board.setContent(prompt.input("내용? "));
    board.setWriter(prompt.input("작성자? "));

    Connection con = null;

    try {
      con = threadConnection.getConnection();
      con.setAutoCommit(false);

      boardDao.add(board);
      boardDao.add(board);

      TimeUnit.SECONDS.sleep(10);
      boardDao.add(board);

      con.commit();
    } catch (Exception e) {
      try {con.rollback();} catch (Exception e2) {}
    }
  }
}
