package bitcamp.myapp.handler.board;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.repository.BoardRepository;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;

// 게시글의 '조회' 메뉴를 선택했을 때 작업을 수행하는 클래스
public class BoardViewHandler implements MenuHandler {

  Prompt prompt;
  BoardRepository boardRepository;

  public BoardViewHandler(BoardRepository boardRepository, Prompt prompt) {
    this.prompt = prompt;
    this.boardRepository = boardRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    Board board = this.boardRepository.get(index);

    if (board == null) {
      System.out.println("게시글 번호가 유효하지 않습니다.");
      return;
    }

    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("작성자: %s\n", board.writer);
    System.out.printf("작성일: %s\n", board.createdDate);
  }
}
