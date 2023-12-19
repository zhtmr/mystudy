package bitcamp.myapp.handler.assignment;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Assignment;

public class AssignmentModifyHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository objectRepository;

  public AssignmentModifyHandler(Prompt prompt, ObjectRepository objectRepository) {
    this.prompt = prompt;
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    Assignment oldVal = (Assignment) objectRepository.get(index);
    if (oldVal == null) {
      System.out.println("과제 번호가 유효하지 않습니다.");
      return;
    }

    Assignment assignment = new Assignment();
    assignment.setTitle(this.prompt.input("과제명(%s)? ", assignment.getTitle()));
    assignment.setContent(this.prompt.input("내용(%s)? ", assignment.getContent()));
    assignment.setDeadline(this.prompt.input("제출 마감일(%s)? ", assignment.getDeadline()));

    objectRepository.set(index, assignment);
  }
}
