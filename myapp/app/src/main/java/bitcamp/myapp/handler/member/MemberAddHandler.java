package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.repository.MemberRepository;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Member;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberAddHandler implements MenuHandler {

  Prompt prompt;
  MemberRepository memberRepository;

  public MemberAddHandler(MemberRepository memberRepository, Prompt prompt) {
    this.memberRepository = memberRepository;
    this.prompt = prompt;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    Member member = new Member();
    member.email = this.prompt.input("이메일? ");
    member.name = this.prompt.input("이름? ");
    member.password = this.prompt.input("암호? ");
    member.createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    memberRepository.add(member);
  }
}
