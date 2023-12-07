package bitcamp.myapp.mytest;

import static bitcamp.myapp.mytest.Category.toCategory;
import static bitcamp.myapp.mytest.SubMenu.toSubMenu;

import java.util.Objects;
import java.util.Scanner;

public class App {

  static final String ANSI_CLEAR = "\033[0m";
  static final String ANSI_BOLD_RED = "\033[1;31m";
  static final String ANSI_RED = "\033[0;31m";
  static final String[] MENUS = {"1. 과제", "2. 게시글", "3. 도움말", "4. 종료"};
  static final String APP_TITLE = ANSI_BOLD_RED + "[과제 관리 시스템]" + ANSI_CLEAR;


  public static void main(String[] args) {
    String sub = "";

    printMainMenu();
    Scanner keyboard = new Scanner(System.in);

    loop:
    while (true) {
      Category input = toCategory(prompt(keyboard, sub));
      switch (input) {
        case PROBLEM, POSTS:
          printSubMenu(input, keyboard);
          break;
        case HELP:
          System.out.println("도움말입니다.");
          break;
        case END:
          System.out.println("종료합니다");
          break loop;
        case MENU:
          printMainMenu();
          break;
        default:
          System.out.println("다시 선택해주세요");
      }
    }
    keyboard.close();
  }

  private static void crlf() {
    System.out.println();
  }


  static String prompt(Scanner keyboard, String sub) {
    System.out.print(Objects.equals(sub, "") ? "메인 > " : "메인/" + sub + "> ");
    return keyboard.nextLine();
  }

  static void printSubMenu(Category menu, Scanner keyboard) {
    String menuName = getSubMenuList(menu);
    crlf();

    while (true) {
      SubMenu input = toSubMenu(prompt(keyboard, menuName));
      switch (input) {
        case PREV:
          return;
        case MENU:
          getSubMenuList(menu);
          break;
        default:
          System.out.println("다시 선택!");
      }
      System.out.println(input.getSubMenuName() + "입니다");
    }

  }

  static String getSubMenuList(Category menu) {
    String menuName = menu.getMenuName();
    String subTitle = "["
        + ANSI_RED
        + menuName
        + ANSI_CLEAR
        + "]";

    System.out.println(subTitle);
    for (SubMenu sub : SubMenu.values()) {
      System.out.println(sub.getInput() + ". " + sub.getSubMenuName());
    }
    return menuName;
  }

  static void printMainMenu() {
    System.out.println(APP_TITLE);
    crlf();
    for (String menu : MENUS) {
      if (Objects.equals(menu, "4. 종료")) {
        System.out.println(ANSI_RED + menu + ANSI_CLEAR);
      } else {
        System.out.println(menu);
      }
    }
  }


}

