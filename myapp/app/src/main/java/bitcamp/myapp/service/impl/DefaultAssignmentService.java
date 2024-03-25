package bitcamp.myapp.service.impl;

import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.service.AssignmentService;
import bitcamp.myapp.vo.Assignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultAssignmentService implements AssignmentService {

  private AssignmentDao assignmentDao;

  public DefaultAssignmentService(AssignmentDao assignmentDao) {
    this.assignmentDao = assignmentDao;
  }

  @Transactional
  @Override
  public void add(Assignment assignment) {
    assignmentDao.add(assignment);
  }

  @Override
  public List<Assignment> list() {
    return assignmentDao.findAll();
  }

  @Override
  public Assignment get(int no) {
    return assignmentDao.findBy(no);
  }

  @Transactional
  @Override
  public int update(Assignment assignment) {
    return assignmentDao.update(assignment);
  }

  @Transactional
  @Override
  public int delete(int no) {
    return assignmentDao.delete(no);
  }
}
