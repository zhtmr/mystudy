package bitcamp.myapp;

import bitcamp.myapp.menu.MenuGroup;
import bitcamp.myapp.menu.MenuItem;
import bitcamp.myapp.util.Prompt;

public class App {

  public static void main(String[] args) {
    Prompt prompt = new Prompt(System.in);
//    new MainMenu(prompt).execute();

    MenuGroup mainMenu = new MenuGroup("메인");
    MenuGroup assignmentMenu = new MenuGroup("과제");
    MenuGroup boardMenu = new MenuGroup("게시글");
    MenuGroup memberMenu = new MenuGroup("회원");
    MenuGroup greetingMenu = new MenuGroup("가입인사");
    MenuGroup helpMenu = new MenuGroup("도움말");

    mainMenu.add(assignmentMenu);
    assignmentMenu.add(new MenuItem("등록"));
    assignmentMenu.add(new MenuItem("조회"));
    assignmentMenu.add(new MenuItem("변경"));
    assignmentMenu.add(new MenuItem("삭제"));
    assignmentMenu.add(new MenuItem("목록"));

    mainMenu.add(boardMenu);
    boardMenu.add(new MenuItem("등록"));
    boardMenu.add(new MenuItem("조회"));
    boardMenu.add(new MenuItem("변경"));
    boardMenu.add(new MenuItem("삭제"));
    boardMenu.add(new MenuItem("목록"));

    mainMenu.add(memberMenu);
    memberMenu.add(new MenuItem("등록"));
    memberMenu.add(new MenuItem("조회"));
    memberMenu.add(new MenuItem("변경"));
    memberMenu.add(new MenuItem("삭제"));
    memberMenu.add(new MenuItem("목록"));

    mainMenu.add(greetingMenu);
    greetingMenu.add(new MenuItem("등록"));
    greetingMenu.add(new MenuItem("조회"));
    greetingMenu.add(new MenuItem("변경"));
    greetingMenu.add(new MenuItem("삭제"));
    greetingMenu.add(new MenuItem("목록"));

    mainMenu.add(helpMenu);

    mainMenu.execute(prompt);
    prompt.close();
  }
}
