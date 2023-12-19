package bitcamp.myapp.handler.assignment;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.AnsiEscape;
import bitcamp.myapp.util.ObjectRepository;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Assignment;

public class AssignmentAddHandler implements MenuHandler {

  Prompt prompt;
  ObjectRepository objectRepository;

  public AssignmentAddHandler(Prompt prompt, ObjectRepository objectRepository) {
    this.prompt = prompt;
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR,
        menu.getTitle());

    Assignment assignment = new Assignment();
    assignment.setTitle(this.prompt.input("과제명? "));
    assignment.setContent(this.prompt.input("내용? "));
    assignment.setDeadline(this.prompt.input("제출 마감일? "));

    objectRepository.add(assignment);
  }
}
