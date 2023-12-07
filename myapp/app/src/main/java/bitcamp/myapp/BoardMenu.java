package bitcamp.myapp;

public class BoardMenu {

  String title;
  Board[] boards = new Board[3];
  int length = 0;

  // 기본생성자 대신 BoardMenu 인스턴스 생성 시 title 초기화하는 생성자를 사용한다.
  // -> 인스턴스 생성 시 무조건 값 넘겨야됨. 안넘기면 컴파일 단계에서 오류 발생시킴.
  // -> 개발자의 실수를 줄일 수 있다.
  public BoardMenu(String title) {
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

  void execute() {
    this.printMenu();
    while (true) {
      String input = Prompt.input("메인/%s> ", this.title);

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

  void delete() {
    System.out.printf("%s 삭제:\n", this.title);

    int index = Integer.parseInt(Prompt.input("번호? "));
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다.\n", this.title);
      return;
    }

    for (int i = index; i < this.length - 1; i++) {
      this.boards[i] = this.boards[i + 1];
    }
    this.boards[--this.length] = null;
  }

  void modify() {
    System.out.printf("%s 변경:\n", this.title);

    int index = Integer.parseInt(Prompt.input("번호? "));
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다.\n", this.title);
      return;
    }

    Board board = this.boards[index];
    board.title = Prompt.input("제목(%s)? ", board.title);
    board.content = Prompt.input("내용(%s)? ", board.content);
    board.writer = Prompt.input("작성자(%s)? ", board.writer);
    board.createdDate = Prompt.input("작성일(%s)? ", board.createdDate);
  }

  void view() {
    System.out.println("게시글 조회:");

    int index = Integer.parseInt(Prompt.input("번호? "));
    if (index < 0 || index >= this.length) {
      System.out.println("게시물 번호가 유효하지 않습니다.");
      return;
    }

    Board board = this.boards[index];
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("작성자: %s\n", board.writer);
    System.out.printf("작성일: %s\n", board.createdDate);
  }

  void list() {
    System.out.println("게시글 목록:");
    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n", "제목", "내용", "작성자", "작성일");

    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n", board.title, board.content, board.writer,
          board.createdDate);
    }
  }

  void add() {
    System.out.println("게시글 등록:");

    if (this.length == this.boards.length) {
      int oldSize = this.boards.length;
      int newSize = oldSize + (oldSize / 2);
      Board[] arr = new Board[newSize];

      for (int i = 0; i < oldSize; i++) {
        arr[i] = boards[i];
      }
      this.boards = arr;
    }

    Board board = new Board();
    board.title = Prompt.input("제목? ");
    board.content = Prompt.input("내용? ");
    board.writer = Prompt.input("작성자? ");
    board.createdDate = Prompt.input("작성일? ");

    boards[length++] = board;
  }
}
