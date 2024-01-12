package bitcamp.myapp.dao.json;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import java.util.List;

public class BoardDaoImpl extends AbstractDao<Board> implements BoardDao {

  private int sequence;

  public BoardDaoImpl(String filepath) {
    super(filepath);
    // 마지막 게시글의 식별 번호를 알아낸다.
    sequence = list.getLast().getNo();
  }

  @Override
  public void add(Board board) {
    board.setNo(++sequence);
    this.list.add(board);
    saveData();
  }

  @Override
  public int delete(int no) {
    int index = indexOf(no);
    if (index == -1) {
      return 0;
    }
    list.remove(index);
    saveData();
    return 1;
  }

  @Override
  public List<Board> findAll() {
    return this.list.subList(0, list.size());  // 원본 list 와 별개로 조회용 반환
  }

  @Override
  public Board findBy(int no) {
    int index = indexOf(no);
    if (index == -1) {
      return null;
    }
    return list.get(index);
  }

  @Override
  public int update(Board board) {
    int index = indexOf(board.getNo());
    if (index == -1) {
      return 0;
    }
    list.set(index, board);
    saveData();
    return 1;
  }

  private int indexOf(int no) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
