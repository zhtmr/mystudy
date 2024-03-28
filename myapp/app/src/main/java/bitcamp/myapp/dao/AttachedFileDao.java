package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachedFileDao {

  void add(AttachedFile file);

  int addAll(List<AttachedFile> files);

  int delete(int no);

  int deleteAll(int boardNo);

  int update(List<AttachedFile> files);

  List<AttachedFile> findAllByBoardNo(int boardNo);

  AttachedFile findByNo(int no);
}
