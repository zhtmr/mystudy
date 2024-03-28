package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Assignment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssignmentDao {

  void add(Assignment assignment);

  int delete(int no);

  List<Assignment> findAll();

  Assignment findBy(int no);

  int update(Assignment assignment);

}
