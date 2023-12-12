package bitcamp.myapp.repository;

import bitcamp.myapp.vo.Assignment;

public class AssignmentRepository {

  private Assignment[] assignments = new Assignment[3];
  private int length = 0;

  public void add(Assignment assignment) {
    if (this.length == this.assignments.length) {
      int oldSize = this.assignments.length;
      int newSize = oldSize + (oldSize / 2);

      // 이전 배열에 들어 있는 값을 새 배열에 복사
      Assignment[] arr = new Assignment[newSize];
      for (int i = 0; i < oldSize; i++) {
        arr[i] = this.assignments[i];
      }

      // 새 배열을 가리키도록 배열 레퍼런스를 변경
      this.assignments = arr;
    }
    this.assignments[length++] = assignment;
  }

  public Assignment[] toArray() {
    Assignment[] assignments = new Assignment[this.length];
    for (int i = 0; i < this.length; i++) {
      assignments[i] = this.assignments[i];
    }
    return assignments;
  }


  public Assignment get(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    return this.assignments[index];
  }


  public Assignment set(int index, Assignment assignment) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    Assignment old = this.assignments[index];
    this.assignments[index] = assignment;
    return old;
  }

  public Assignment remove(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    Assignment deleted = this.assignments[index];
    for (int i = index; i < (this.length - 1); i++) {
      this.assignments[i] = this.assignments[i + 1];
    }

    this.assignments[--this.length] = null;
    return deleted;
  }
}
