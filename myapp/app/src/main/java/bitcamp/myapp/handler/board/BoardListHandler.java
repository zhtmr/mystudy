package bitcamp.myapp.handler.board;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.vo.Board;

// 게시글의 '목록' 메뉴를 선택했을 때 작업을 수행하는 클래스
public class BoardListHandler implements MenuHandler {

  ObjectRepository objectRepository;

  public BoardListHandler(ObjectRepository objectRepository) {
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());
    System.out.printf("%-20s\t%10s\t%s\n", "Title", "Writer", "Date");

    for (Object object : objectRepository.toArray()) {
      Board board = (Board) object;
      System.out.printf("%-20s\t%10s\t%s\n", board.getTitle(), board.getWriter(),
          board.getCreatedDate());
    }
  }
}
