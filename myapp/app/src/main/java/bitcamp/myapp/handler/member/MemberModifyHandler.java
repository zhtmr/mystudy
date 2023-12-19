package bitcamp.myapp.handler.member;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Member;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberModifyHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository objectRepository;

  public MemberModifyHandler(ObjectRepository objectRepository, Prompt prompt) {
    this.prompt = prompt;
    this.objectRepository = objectRepository;
  }


  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    Member oldVal = (Member) objectRepository.get(index);
    if (oldVal == null) {
      System.out.println("멤버 번호가 유효하지 않습니다");
      return;
    }

    Member member = new Member();
    member.setEmail(this.prompt.input("이메일(%s): ", oldVal.getEmail()));
    member.setName(this.prompt.input("이름(%s): ", oldVal.getName()));
    member.setPassword(this.prompt.input("암호: ", oldVal.getPassword()));
    member.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    objectRepository.set(index, member);
  }
}
