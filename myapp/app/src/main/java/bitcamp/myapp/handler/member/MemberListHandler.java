package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.repository.MemberRepository;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.vo.Member;

public class MemberListHandler implements MenuHandler {

  MemberRepository memberRepository;

  public MemberListHandler(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    System.out.printf("%-20s\t%20s\t%15s\n", "이메일", "이름", "가입일");
    for (int i = 0; i < this.memberRepository.length; i++) {
      Member member = this.memberRepository.members[i];
      System.out.printf("%-20s\t%20s\t%15s\n", member.email, member.name, member.createDate);
    }
  }
}
