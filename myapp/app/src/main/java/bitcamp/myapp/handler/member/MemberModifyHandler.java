package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.repository.MemberRepository;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Member;

public class MemberModifyHandler implements MenuHandler {

  Prompt prompt;
  MemberRepository memberRepository;

  public MemberModifyHandler(MemberRepository memberRepository, Prompt prompt) {
    this.prompt = prompt;
    this.memberRepository = memberRepository;
  }


  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    if (index < 0 || index >= this.memberRepository.length) {
      System.out.println("멤버 번호가 유효하지 않습니다");
      return;
    }

    Member member = this.memberRepository.members[index];
    member.email = this.prompt.input("이메일(%s): ", member.email);
    member.name = this.prompt.input("이름(%s): ", member.name);
    member.password = this.prompt.input("암호: ", member.password);
  }
}
