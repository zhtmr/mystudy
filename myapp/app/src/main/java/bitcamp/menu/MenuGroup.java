package bitcamp.menu;

import bitcamp.util.LinkedList;
import bitcamp.util.Prompt;

// Composite 패턴에서 '복합 객체(composite object)' 역할을 하는 클래스
// - 다른 Menu 객체를 포함한다.
public class MenuGroup extends AbstractMenu {

  private LinkedList<Menu> menus = new LinkedList<>();

  public MenuGroup(String title) {
    super(title);
  }

  @Override
  public void execute(Prompt prompt) {
    this.printMenu();

    while (true) {
      String input = prompt.input("%s> ", this.getTitle());

      if (input.equals("menu")) {
        this.printMenu();
        continue;
      } else if (input.equals("0")) {
        break;
      }

      try {
        int menuNo = Integer.parseInt(input);
        if (menuNo < 1 || menuNo > menus.size()) {
          System.out.println("메뉴 번호가 옳지 않습니다.");
          continue;
        }

        this.menus.get(menuNo - 1).execute(prompt);
      } catch (Exception e) {
        System.out.println("메뉴가 옳지 않습니다");
      }
    }
  }

  private void printMenu() {
    System.out.printf("[%s]\n", this.getTitle());

    for (int i = 0; i < this.menus.size(); i++) {
      System.out.printf("%d. %s\n", (i + 1), menus.get(i).getTitle());
    }

    System.out.printf("0. %s\n", "이전");
  }

  public void add(Menu menu) {
    this.menus.add(menu);
  }

  public void remove(Menu menu) {
    this.menus.remove(menu);
  }

}
