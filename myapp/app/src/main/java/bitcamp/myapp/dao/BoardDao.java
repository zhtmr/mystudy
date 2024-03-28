package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {

  void add(Board board);

  int delete(int no);

  List<Board> findAll(int category);

  Board findBy(int no);

  int update(Board board);

}
