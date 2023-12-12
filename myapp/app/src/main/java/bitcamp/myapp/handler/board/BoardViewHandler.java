package bitcamp.myapp.handler.board;

import bitcamp.menu.MenuHandler;
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
  public void action() {
    System.out.println("게시글 조회:");

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.boardRepository.length) {
      System.out.printf("%s 번호가 유효하지 않습니다.\n", this.boardRepository.boards[index].title);
      return;
    }

    Board board = this.boardRepository.boards[index];
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("작성자: %s\n", board.writer);
    System.out.printf("작성일: %s\n", board.createdDate);
  }
}
