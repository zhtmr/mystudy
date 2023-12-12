package bitcamp.myapp.handler.member;

import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Member;

public class MemberViewHandler implements MenuHandler {

  Prompt prompt;
  MemberRepository memberRepository;

  public MemberViewHandler(MemberRepository memberRepository, Prompt prompt) {
    this.memberRepository = memberRepository;
    this.prompt = prompt;
  }

  @Override
  public void action() {
    System.out.println("멤버 조회:");

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.memberRepository.length) {
      System.out.println("멤버 번호가 유효하지 않습니다");
      return;
    }
    Member member = this.memberRepository.members[index];
    System.out.printf("이메일: %s\n", member.email);
    System.out.printf("이름: %s\n", member.name);
    System.out.printf("가입일: %s\n", member.createDate);
  }
}
