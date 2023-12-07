package bitcamp.myapp.mytest;

public enum Category {

  PROBLEM("1", "과제"),
  POSTS("2", "게시글"),
  HELP("3", "도움말"),
  END("4", "종료"),
  MENU("menu", "메뉴"),
  ERR("", "");;


  private String input;
  private String menuName;

  Category(String input, String menuName) {
    this.input = input;
    this.menuName = menuName;
  }


  public static Category toCategory(String input) {
    Category[] categories = Category.values();
    for (Category category : categories) {
      if (category.getInput().equals(input)) {
        return category;
      }
    }
    return Category.ERR;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }
}
