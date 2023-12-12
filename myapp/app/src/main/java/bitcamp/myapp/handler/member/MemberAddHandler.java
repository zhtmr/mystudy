package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Member;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberAddHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository objectRepository;

  public MemberAddHandler(ObjectRepository objectRepository, Prompt prompt) {
    this.objectRepository = objectRepository;
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

    objectRepository.add(member);
  }
}
