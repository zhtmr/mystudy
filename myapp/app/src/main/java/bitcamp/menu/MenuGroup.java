package bitcamp.menu;

import bitcamp.myapp.vo.Member;
import bitcamp.util.AnsiEscape;
import bitcamp.util.Prompt;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// Composite 패턴에서 '복합 객체(composite object)' 역할을 하는 클래스
// - 다른 Menu 객체를 포함한다.
public class MenuGroup extends AbstractMenu {

  private List<Menu> menus = new LinkedList<>();

  private MenuGroup(String title) {
    super(title);
  }

  // 팩토리 메서드
  public static MenuGroup getInstance(String title) {
    return new MenuGroup(title);
  }

  @Override
  public void execute(Prompt prompt) throws Exception {
    // 메뉴를 실행할 때 메뉴의 제목을 breadcrumb 에 추가한다.
    prompt.pushPath(title);
    this.printMenu(prompt);

    while (true) {
      String input = prompt.input("%s%s>", getLoginUsername(prompt), prompt.getFullPath());

      if (input.equals("menu")) {
        this.printMenu(prompt);
        continue;
      } else if (input.equals("0")) {
        break;
      }

      try {
        int menuNo = Integer.parseInt(input);
        if (menuNo < 1 || menuNo > menus.size()) {
          prompt.println("메뉴 번호가 옳지 않습니다.");
          continue;
        }

        this.menus.get(menuNo - 1).execute(prompt);
      } catch (Exception e) {
        prompt.println("메뉴가 옳지 않습니다");
      }
    }

    prompt.popPath(); // 메뉴 나갈때 메뉴 제목을 제거한다.
  }

  private String getLoginUsername(Prompt prompt) {
    Member loginUser = (Member) prompt.getSession().getAttribute("loginUser");
    if (loginUser != null) {
      return AnsiEscape.ANSI_BOLD_RED + loginUser.getName() + ":" + AnsiEscape.ANSI_CLEAR;
    } else {
      return "";
    }
  }

  private void printMenu(Prompt prompt) {

    prompt.printf("[%s]\n", this.getTitle());

    Iterator<Menu> iterator = this.menus.iterator();
    int i = 1;
    while (iterator.hasNext()) {
      Menu menu = iterator.next();
      prompt.printf("%d. %s\n", i++, menu.getTitle());
    }

    prompt.printf("0. %s\n", "이전");
  }

  public void add(Menu menu) {
    this.menus.add(menu);
  }

  public MenuItem addItem(String title, MenuHandler handler) {
    MenuItem menuItem = new MenuItem(title, handler);
    add(menuItem);
    return menuItem;
  }

  public MenuGroup addGroup(String title) {
    MenuGroup menuGroup = new MenuGroup(title);
    add(menuGroup);
    return menuGroup;
  }

  public void remove(Menu menu) {
    this.menus.remove(menu);
  }

}
