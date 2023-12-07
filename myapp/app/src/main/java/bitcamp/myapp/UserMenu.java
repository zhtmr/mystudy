package bitcamp.myapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMenu {

  static User[] users = new User[3];
  static int length = 0;

  static void printMenu() {
    System.out.println("[회원]");
    System.out.println("1. 등록");
    System.out.println("2. 조회");
    System.out.println("3. 변경");
    System.out.println("4. 삭제");
    System.out.println("5. 목록");
    System.out.println("0. 이전");
  }

  public static void execute() {
    printMenu();

    while (true) {
      String input = Prompt.input("메인/회원> ");

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

  static void add() {
    System.out.println("회원 가입:");

    if (length == users.length) {
      int oldSize = users.length;
      int newSize = oldSize + (oldSize >> 1);
      User[] arr = new User[newSize];

      System.arraycopy(users, 0, arr, 0, oldSize);

      users = arr;
    }

    User user = new User();
    user.email = Prompt.input("이메일? ");
    user.name = Prompt.input("이름? ");
    user.password = Prompt.input("암호? ");
    user.createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    users[length++] = user;
  }

  static void list() {
    System.out.println("회원 목록:");

    System.out.printf("%-30s\t%10s\t%20s\n", "이메일", "이름", "가입일");
    for (int i = 0; i < length; i++) {
      User user = users[i];
      System.out.printf("%-30s\t%10s\t%20s\n", user.email, user.name, user.createDate);
    }
  }

  static void view() {
    System.out.println("회원 상세:");

    int index = Integer.parseInt(Prompt.input("번호? "));
    if (index < 0 || index >= length) {
      System.out.println("잘못된 번호 입력");
      return;
    }

    User user = users[index];
    System.out.printf("이메일 : %s\n", user.email);
    System.out.printf("이름 : %s\n", user.name);
    System.out.printf("가입일 : %s\n", user.createDate);
  }

  static void delete() {
    System.out.println("회원 삭제:");

    int index = Integer.parseInt(Prompt.input("번호? "));
    if (index < 0 || index >= length) {
      System.out.println("잘못된 번호 입력");
      return;
    }

    for (int i = index; i < length - 1; i++) {
      users[i] = users[i + 1];
    }
    //length--;
    users[--length] = null;
  }

  static void modify() {
    System.out.println("회원 수정:");

    int index = Integer.parseInt(Prompt.input("번호? "));
    if (index < 0 || index >= length) {
      System.out.println("잘못된 번호 입력");
      return;
    }

    User user = users[index];
    user.email = Prompt.input("이메일(%s)? ", user.email);
    user.name = Prompt.input("이름(%s)? ", user.name);
    user.password = Prompt.input("새 암호? ");

  }

}
