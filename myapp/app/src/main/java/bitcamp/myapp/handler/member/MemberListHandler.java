package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.vo.Member;

public class MemberListHandler implements MenuHandler {

  ObjectRepository objectRepository;

  public MemberListHandler(ObjectRepository objectRepository) {
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    System.out.printf("%-20s\t%20s\t%15s\n", "이메일", "이름", "가입일");
    for (Object object : objectRepository.toArray()) {
      Member member = (Member) object; // downcasting 필수! 컴파일 오류 발생!
      System.out.printf("%-20s\t%20s\t%15s\n", member.email, member.name, member.createDate);
    }
  }
}
