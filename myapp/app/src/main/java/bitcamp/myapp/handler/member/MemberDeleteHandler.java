package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Member;

public class MemberDeleteHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository<Member> objectRepository;

  public MemberDeleteHandler(ObjectRepository<Member> objectRepository, Prompt prompt) {
    this.objectRepository = objectRepository;
    this.prompt = prompt;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    if (objectRepository.remove(index) == null) {
      System.out.println("멤버 번호가 유효하지 않습니다");
    }
  }
}
