package bitcamp.myapp.handler.assignment;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;

public class AssignmentDeleteHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository objectRepository;

  public AssignmentDeleteHandler(Prompt prompt, ObjectRepository objectRepository) {
    this.prompt = prompt;
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    int index = this.prompt.inputInt("번호? ");
    if (this.objectRepository.remove(index) == null) {
      System.out.println("과제 번호가 유효하지 않습니다.");
    }
  }
}
