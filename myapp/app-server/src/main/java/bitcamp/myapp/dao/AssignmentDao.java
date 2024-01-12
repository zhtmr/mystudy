package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Assignment;

import java.util.List;

public interface AssignmentDao {


  List<Assignment> findAll();

  Assignment findBy(int no);

  void add(Assignment assignment);

  int delete(int no);

  int update(Assignment assignment);


}
