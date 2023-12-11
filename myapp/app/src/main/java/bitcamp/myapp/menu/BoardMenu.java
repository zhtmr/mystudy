package bitcamp.myapp.menu;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardMenu implements Menu {

  Prompt prompt;
  String title;
  Board[] boards = new Board[3];
  int length = 0;

  public BoardMenu(String title, Prompt prompt) {
    this.prompt = prompt;
    this.title = title;
  }

  void printMenu() {
    System.out.printf("[%s]\n", this.title);
    System.out.println("1. 등록");
    System.out.println("2. 조회");
    System.out.println("3. 변경");
    System.out.println("4. 삭제");
    System.out.println("5. 목록");
    System.out.println("0. 이전");
  }

  void add() {
    System.out.printf("%s 등록:\n", this.title);

    if (this.length == this.boards.length) {
      int oldSize = this.boards.length;
      int newSize = oldSize + (oldSize >> 1);

      Board[] arr = new Board[newSize];
      for (int i = 0; i < oldSize; i++) {
        arr[i] = this.boards[i];
      }

      this.boards = arr;
    }

    Board board = new Board();
    board.title = this.prompt.input("제목? ");
    board.content = this.prompt.input("내용? ");
    board.writer = this.prompt.input("작성자? ");
    board.createdDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    this.boards[this.length++] = board;
  }

  void list() {
    System.out.printf("%s 목록:\n", this.title);
    System.out.printf("%-20s\t%10s\t%s\n", "Title", "Writer", "Date");

    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      System.out.printf("%-20s\t%10s\t%s\n", board.title, board.writer, board.createdDate);
    }
  }

  void view() {
    System.out.printf("%s 조회:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다.\n", this.title);
      return;
    }

    Board board = this.boards[index];
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("작성자: %s\n", board.writer);
    System.out.printf("작성일: %s\n", board.createdDate);
  }

  void modify() {
    System.out.printf("%s 변경:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다.\n");
      return;
    }

    Board board = this.boards[index];
    board.title = this.prompt.input("제목(%s)? ", board.title);
    board.content = this.prompt.input("내용(%s)? ", board.content);
    board.writer = this.prompt.input("작성자(%s)? ", board.writer);
  }

  void delete() {
    System.out.printf("%s 삭제:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다.\n");
      return;
    }

    for (int i = index; i < (this.length - 1); i++) {
      this.boards[i] = this.boards[i + 1];
    }
    this.boards[--this.length] = null;
  }

  @Override
  public void execute() {
    this.printMenu();
    while (true) {
      String input = this.prompt.input("메인/%s> ", this.title);

      switch (input) {
        case "1":
          this.add();
          break;
        case "2":
          this.view();
          break;
        case "3":
          this.modify();
          break;
        case "4":
          this.delete();
          break;
        case "5":
          this.list();
          break;
        case "0":
          return;
        case "menu":
          this.printMenu();
          break;
        default:
          System.out.println("메뉴 번호가 옳지 않습니다!");
      }
    }
  }
}