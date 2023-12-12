package bitcamp.myapp.handler.board;

import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;

// 게시글의 '수정' 메뉴를 선택했을 때 작업을 수행하는 클래스
public class BoardModifyHandler implements MenuHandler {

  Prompt prompt;
  BoardRepository boardRepository;

  public BoardModifyHandler(BoardRepository boardRepository, Prompt prompt) {
    this.prompt = prompt;
    this.boardRepository = boardRepository;
  }

  @Override
  public void action() {
    System.out.println("게시글 수정:");

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.boardRepository.length) {
      System.out.println("게시글 번호가 유효하지 않습니다.");
      return;
    }

    Board board = this.boardRepository.boards[index];
    board.title = this.prompt.input("제목(%s)? ", board.title);
    board.content = this.prompt.input("내용(%s)? ", board.content);
    board.writer = this.prompt.input("작성자(%s)? ", board.writer);
  }
}
