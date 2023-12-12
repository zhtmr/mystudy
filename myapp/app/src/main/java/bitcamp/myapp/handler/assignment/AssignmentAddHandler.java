package bitcamp.myapp.handler.assignment;

import bitcamp.menu.Menu;
import bitcamp.menu.MenuHandler;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Assignment;

public class AssignmentAddHandler implements MenuHandler {

  Prompt prompt;
  AssignmentRepository assignmentRepository;

  public AssignmentAddHandler(Prompt prompt, AssignmentRepository assignmentRepository) {
    this.prompt = prompt;
    this.assignmentRepository = assignmentRepository;
  }

  @Override
  public void action(Menu menu) {
    System.out.println("과제 등록:");

    if (this.assignmentRepository.length == this.assignmentRepository.assignments.length) {
      //System.out.println("과제를 더이상 등록할 수 없습니다.");
      int oldSize = this.assignmentRepository.assignments.length;
      int newSize = oldSize + (oldSize / 2);

      // 이전 배열에 들어 있는 값을 새 배열에 복사
      Assignment[] arr = new Assignment[newSize];
      for (int i = 0; i < oldSize; i++) {
        arr[i] = this.assignmentRepository.assignments[i];
      }

      // 새 배열을 가리키도록 배열 레퍼런스를 변경
      this.assignmentRepository.assignments = arr;
    }

    Assignment assignment = new Assignment();
    assignment.title = this.prompt.input("과제명? ");
    assignment.content = this.prompt.input("내용? ");
    assignment.deadline = this.prompt.input("제출 마감일? ");

    this.assignmentRepository.assignments[assignmentRepository.length++] = assignment;
  }
}
