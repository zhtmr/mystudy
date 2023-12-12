package bitcamp.myapp.handler.board;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;

// 게시글의 '수정' 메뉴를 선택했을 때 작업을 수행하는 클래스
public class BoardModifyHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository objectRepository;

  public BoardModifyHandler(ObjectRepository objectRepository, Prompt prompt) {
    this.prompt = prompt;
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    Board oldBoard = (Board) this.objectRepository.get(index);
    if (oldBoard == null) {
      System.out.println("게시글 번호가 유효하지 않습니다.");
      return;
    }

    Board board = new Board();
    board.title = this.prompt.input("제목(%s)? ", oldBoard.title);
    board.content = this.prompt.input("내용(%s)? ", oldBoard.content);
    board.writer = this.prompt.input("작성자(%s)? ", oldBoard.writer);

    this.objectRepository.set(index, board);
  }
}
