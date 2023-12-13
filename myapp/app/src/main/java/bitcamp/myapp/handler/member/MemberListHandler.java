package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.vo.Member;
import java.util.ArrayList;

public class MemberListHandler implements MenuHandler {

  ArrayList<Member> objectRepository;

  public MemberListHandler(ArrayList<Member> objectRepository) {
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    System.out.printf("%-20s\t%20s\t%15s\n", "이메일", "이름", "가입일");
//    Member[] members = new Member[this.objectRepository.size()];
//    this.objectRepository.toArray(members);
    Member[] members = this.objectRepository.toArray(new Member[0]);
    for (Member member : members) {
      System.out.printf("%-20s\t%20s\t%15s\n", member.email, member.name, member.createDate);
    }
  }
}
