package bitcamp.myapp.menu;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Member;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberMenu implements Menu {

  Prompt prompt;
  String title;
  Member[] members = new Member[3];
  int length = 0;

  public MemberMenu(String title, Prompt prompt) {
    this.prompt = prompt;
    this.title = title;
  }

  void add() {
    System.out.printf("%s 등록:\n", this.title);

    if (this.length == this.members.length) {
      int oldSize = this.members.length;
      int newSize = oldSize + (oldSize >> 2);

      Member[] arr = new Member[newSize];
      for (int i = 0; i < this.length; i++) {
        arr[i] = this.members[i];
      }
      this.members = arr;
    }

    Member member = new Member();
    member.email = this.prompt.input("이메일? ");
    member.name = this.prompt.input("이름? ");
    member.password = this.prompt.input("암호? ");
    member.createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    this.members[this.length++] = member;
  }

  void view() {
    System.out.printf("%s 조회:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다\n", this.title);
      return;
    }
    Member member = this.members[index];
    System.out.printf("이메일: %s\n", member.email);
    System.out.printf("이름: %s\n", member.name);
    System.out.printf("가입일: %s\n", member.createDate);
  }

  void modify() {
    System.out.printf("%s 수정:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다\n", this.title);
      return;
    }

    Member member = this.members[index];
    member.email = this.prompt.input("이메일(%s): ", member.email);
    member.name = this.prompt.input("이름(%s): ", member.name);
    member.password = this.prompt.input("암호: ", member.password);
  }

  void delete() {
    System.out.printf("%s 삭제:\n", this.title);

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.length) {
      System.out.printf("%s 번호가 유효하지 않습니다\n", this.title);
      return;
    }

    for (int i = index; i < this.length - 1; i++) {
      this.members[i] = this.members[i + 1];
    }

    this.members[--this.length] = null;
  }

  void list() {
    System.out.printf("%s 목록:\n", this.title);

    System.out.printf("%-20s\t%20s\t%15s\n", "이메일", "이름", "가입일");
    for (int i = 0; i < this.length; i++) {
      Member member = this.members[i];
      System.out.printf("%-20s\t%20s\t%15s\n", member.email, member.name, member.createDate);
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

  @Override
  public void execute(Prompt prompt) {
    this.printMenu();

    while (true) {
      String input = prompt.input("메인/%s> ", this.title);

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
          System.out.println("메뉴 번호 다시!");
      }
    }
  }

  @Override
  public String getTitle() {
    return null;
  }


}
