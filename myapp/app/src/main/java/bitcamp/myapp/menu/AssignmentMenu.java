package bitcamp.myapp.menu;

import bitcamp.myapp.vo.Assignment;
import bitcamp.util.Prompt;

public class AssignmentMenu {

  // 의존객체
  Prompt prompt;
  String title;
  Assignment[] assignments = new Assignment[3];
  int length = 0;

  public AssignmentMenu(String title, Prompt prompt) {
    this.prompt = prompt;
    this.title = title;
  }

  void list() {
    System.out.println("과제 목록:");
    System.out.printf("%-20s\t%s\n", "과제", "제출마감일");

    for (int i = 0; i < this.length; i++) {
      Assignment assignment = this.assignments[i];
      System.out.printf("%-20s\t%s\n", assignment.title, assignment.deadline);
    }
  }

  void view() {
    System.out.println("과제 조회:");

    int index = this.prompt.inputInt("번호? ");

    if (index < 0 || index >= this.length) {
      System.out.println("과제 번호가 유효하지 않습니다.");
      return;
    }

    Assignment assignment = this.assignments[index];
    System.out.printf("과제명: %s\n", assignment.title);
    System.out.printf("내용: %s\n", assignment.content);
    System.out.printf("제출 마감일: %s\n", assignment.deadline);
  }

  void modify() {
    System.out.println("과제 변경:");
    int index = this.prompt.inputInt("번호? ");

    if (index < 0 || index >= this.length) {
      System.out.println("과제 번호가 유효하지 않습니다.");
      return;
    }

    Assignment assignment = this.assignments[index];
    assignment.title = this.prompt.input("과제명(%s)? ", assignment.title);
    assignment.content = this.prompt.input("내용(%s)? ", assignment.content);
    assignment.deadline = this.prompt.input("제출 마감일(%s)? ", assignment.deadline);

  }

  void delete() {
    System.out.println("과제 삭제:");

    int index = this.prompt.inputInt("번호? ");

    if (index < 0 || index >= this.length) {
      System.out.println("과제 번호가 유효하지 않습니다.");
      return;
    }

    for (int i = index; i < this.length - 1; i++) {
      this.assignments[i] = this.assignments[i + 1];
    }
    this.assignments[--this.length] = null;
  }

  void add() {
    System.out.println("과제 등록:");

    if (this.length == this.assignments.length) {
      int oldSize = this.assignments.length;
      int newSize = oldSize + (oldSize / 2);
      Assignment[] arr = new Assignment[newSize];

      // 이전 배열 새 배열에 복사
      System.arraycopy(this.assignments, 0, arr, 0, oldSize);
      this.assignments = arr;
//      return;
    }

    Assignment assignment = new Assignment();
    assignment.title = this.prompt.input("과제명? ");
    assignment.content = this.prompt.input("내용? ");
    assignment.deadline = this.prompt.input("제출 마감일? ");

    this.assignments[this.length++] = assignment;
  }

  void execute() {
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

  void printMenu() {
    System.out.printf("[%s]\n", this.title);
    System.out.println("1. 등록");
    System.out.println("2. 조회");
    System.out.println("3. 변경");
    System.out.println("4. 삭제");
    System.out.println("5. 목록");
    System.out.println("0. 이전");
  }
}
