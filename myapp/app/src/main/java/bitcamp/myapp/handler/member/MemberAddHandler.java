package bitcamp.myapp.handler.member;

import bitcamp.menu.MenuHandler;
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
  public void action() {
    System.out.println("멤버 등록:");

    if (this.memberRepository.length == this.memberRepository.members.length) {
      int oldSize = this.memberRepository.members.length;
      int newSize = oldSize + (oldSize >> 2);

      Member[] arr = new Member[newSize];
      for (int i = 0; i < this.memberRepository.length; i++) {
        arr[i] = this.memberRepository.members[i];
      }
      this.memberRepository.members = arr;
    }

    Member member = new Member();
    member.email = this.prompt.input("이메일? ");
    member.name = this.prompt.input("이름? ");
    member.password = this.prompt.input("암호? ");
    member.createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    this.memberRepository.members[this.memberRepository.length++] = member;
  }
}
