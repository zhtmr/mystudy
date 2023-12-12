package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.vo.Member;

public class MemberListHandler implements MenuHandler {

  MemberRepository memberRepository;

  public MemberListHandler(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.println("멤버 목록:");

    System.out.printf("%-20s\t%20s\t%15s\n", "이메일", "이름", "가입일");
    for (int i = 0; i < this.memberRepository.length; i++) {
      Member member = this.memberRepository.members[i];
      System.out.printf("%-20s\t%20s\t%15s\n", member.email, member.name, member.createDate);
    }
  }
}
