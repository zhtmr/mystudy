package bitcamp.menu;

// 메뉴를 실행시킬 때 작업을 수행할 객체의 사용 규칙 정의
public interface MenuHandler {

  // 사용자가 메뉴를 선택하면, MenuItem 객체를 다음 규칙에 따라 호출
  public abstract void action(Menu menu);

}
