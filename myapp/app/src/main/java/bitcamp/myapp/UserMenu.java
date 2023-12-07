package bitcamp.myapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMenu {

  Prompt prompt;
  String title;
  User[] users = new User[3];
  int length = 0;

  public UserMenu(String title, Prompt prompt) {
    this.prompt = prompt;
    this.title = title;
  }

  void list() {
    System.out.printf("%s회원 목록:\n", this.title);

    System.out.printf("%-30s\t%10s\t%20s\n", "이메일", "이름", "가입일");
    for (int i = 0; i < this.length; i++) {
      User user = this.users[i];
      System.out.printf("%-30s\t%10s\t%20s\n", user.email, user.name, user.createDate);
    }
  }

  void view() {
    System.out.printf("%s회원 상세:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.println("잘못된 번호 입력");
      return;
    }

    User user = this.users[index];
    System.out.printf("이메일 : %s\n", user.email);
    System.out.printf("이름 : %s\n", user.name);
    System.out.printf("가입일 : %s\n", user.createDate);
  }

  void delete() {
    System.out.printf("%s회원 삭제:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.println("잘못된 번호 입력");
      return;
    }

    for (int i = index; i < this.length - 1; i++) {
      this.users[i] = this.users[i + 1];
    }
    this.users[--this.length] = null;
  }

  void modify() {
    System.out.printf("%s회원 수정:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= length) {
      System.out.println("잘못된 번호 입력");
      return;
    }

    User user = this.users[index];
    user.email = this.prompt.input("이메일(%s)? ", user.email);
    user.name = this.prompt.input("이름(%s)? ", user.name);
    user.password = this.prompt.input("새 암호? ");

  }

  void execute() {
    printMenu();

    while (true) {
      String input = this.prompt.input("메인/%s> ", this.title);

      switch (input) {
        case "1":
          add();
          break;
        case "2":
          view();
          break;
        case "3":
          modify();
          break;
        case "4":
          delete();
          break;
        case "5":
          list();
          break;
        case "0":
          return;
        case "menu":
          printMenu();
          break;
        default:
          System.out.println("잘못된 번호입니다.");
      }
    }

  }

  void add() {
    System.out.printf("%s회원 가입:\n", this.title);

    if (this.length == this.users.length) {
      int oldSize = this.users.length;
      int newSize = oldSize + (oldSize >> 1);
      User[] arr = new User[newSize];

      System.arraycopy(this.users, 0, arr, 0, oldSize);

      this.users = arr;
    }

    User user = new User();
    user.email = this.prompt.input("이메일? ");
    user.name = this.prompt.input("이름? ");
    user.password = this.prompt.input("암호? ");
    user.createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    this.users[this.length++] = user;
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
