package bitcamp.myapp.handler.board;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;
import java.text.SimpleDateFormat;
import java.util.Date;

// 게시글의 '등록' 메뉴를 선택했을 때 작업을 수행하는 클래스
public class BoardAddHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository objectRepository;

  public BoardAddHandler(ObjectRepository objectRepository, Prompt prompt) {
    this.objectRepository = objectRepository;
    this.prompt = prompt;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    Board board = new Board();
    board.setTitle(this.prompt.input("제목? "));
    board.setContent(this.prompt.input("내용? "));
    board.setWriter(this.prompt.input("작성자? "));
    board.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

    // 캡슐화
    objectRepository.add(board);

  }
}
