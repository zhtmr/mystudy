package bitcamp.myapp.handler.board;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.util.Prompt;

public class BoardDeleteHandler extends AbstractMenuHandler {

  private BoardDao boardDao;

  public BoardDeleteHandler(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  protected void action(Prompt prompt) {
    try {
      int no = prompt.inputInt("번호? ");
      if (boardDao.delete(no) == 0) {
        prompt.println("게시글 번호가 유효하지 않습니다.");
      } else {
        prompt.println("삭제했습니다.");
      }
    } catch (Exception e) {
      prompt.println("게시글 삭제 중 오류 발생!");
      prompt.println("다시 입력해주세요");
    }
  }
}
