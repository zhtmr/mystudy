package bitcamp.myapp.mytest;

public enum SubMenu {
  // {"1. 등록", "2. 조회", "3. 변경", "4. 삭제", "0. 이전"};
  REGISTER("1", "등록"),
  FIND("2", "조회"),
  CHANGE("3", "변경"),
  DELETE("4", "삭제"),
  PREV("0", "이전"),
  MENU("menu", "메뉴"),
  ERR("", "");

  private String input;
  private String subMenuName;

  SubMenu(String input, String value) {
    this.input = input;
    this.subMenuName = value;
  }

  public static SubMenu toSubMenu(String input) {
    SubMenu[] subMenus = SubMenu.values();
    for (SubMenu subMenu : subMenus) {
      if (subMenu.getInput().equals(input)) {
        return subMenu;
      }
    }
    return SubMenu.ERR;
  }

  public String getInput() {
    return input;
  }

  public String getSubMenuName() {
    return subMenuName;
  }
}
