package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import org.junit.jupiter.api.Test;

class AbstractDaoTest extends AbstractDao<Board> {

  @Test
  void main() {
    AbstractDaoTest obj = new AbstractDaoTest();
    obj.loadData("../board.json");

    for (Board board : obj.list) {
      System.out.println("board = " + board);
    }
  }
}
