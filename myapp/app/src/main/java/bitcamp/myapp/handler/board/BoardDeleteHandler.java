package bitcamp.myapp.handler.board;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.repository.BoardRepository;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.Prompt;

// 게시글의 '삭제' 메뉴를 선택했을 때 작업을 수행하는 클래스
public class BoardDeleteHandler implements MenuHandler {

  Prompt prompt;
  BoardRepository boardRepository;

  public BoardDeleteHandler(BoardRepository boardRepository, Prompt prompt) {
    this.prompt = prompt;
    this.boardRepository = boardRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    if (this.boardRepository.remove(index) == null) {
      System.out.println("게시글 번호가 유효하지 않습니다)");
    }
  }
}
