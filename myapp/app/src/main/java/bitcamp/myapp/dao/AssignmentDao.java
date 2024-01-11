package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Assignment;

import java.util.List;

public class AssignmentDao extends AbstractDao<Assignment> {

  private int sequence;

  public AssignmentDao(String filepath) {
    super(filepath);
    sequence = list.getLast().getNo();
  }

  public List<Assignment> findAll() {
    return list.subList(0, list.size());
  }

  public Assignment findBy(int no) {
    int index = indexOf(no);
    if (index == -1) {
      return null;
    }
    return list.get(index);
  }

  public void add(Assignment assignment) {
    assignment.setNo(++sequence);
    list.add(assignment);
    saveData();
  }

  public int delete(int no) {
    int index = indexOf(no);
    if (index == -1) {
      return 0;
    }
    list.remove(index);
    saveData();
    return 1;
  }

  public int update(Assignment assignment) {
    int index = indexOf(assignment.getNo());
    if (index == -1) {
      return 0;
    }
    list.set(index, assignment);
    saveData();
    return 1;
  }

  public int indexOf(int no) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == no) {
        return i;
      }
    }
    return -1;
  }

}
