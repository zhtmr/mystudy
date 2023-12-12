package bitcamp.myapp.handler.board;

import bitcamp.menu.MenuHandler;
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
  public void action() {
    System.out.println("게시글 삭제!");

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.boardRepository.length) {
      System.out.printf("%s 번호가 유효하지 않습니다.\n");
      return;
    }

    for (int i = index; i < (this.boardRepository.length - 1); i++) {
      this.boardRepository.boards[i] = this.boardRepository.boards[i + 1];
    }
    this.boardRepository.boards[--this.boardRepository.length] = null;
  }
}
