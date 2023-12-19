package bitcamp.myapp.handler.assignment;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.menu.Menu;
import bitcamp.myapp.vo.Assignment;
import java.util.ArrayList;

public class AssignmentListHandler extends AbstractMenuHandler {

  private ArrayList<Assignment> objectRepository;

  public AssignmentListHandler(ArrayList<Assignment> objectRepository) {
    this.objectRepository = objectRepository;
  }

  @Override
  public void action(Menu menu) {
    super.action(menu);
    System.out.printf("%-20s\t%s\n", "과제", "제출마감일");

    Assignment[] assignments = new Assignment[this.objectRepository.size()];
    this.objectRepository.toArray(assignments);

    for (Assignment assignment : assignments) {
      System.out.printf("%-20s\t%s\n", assignment.getTitle(), assignment.getDeadline());
    }
  }
}
