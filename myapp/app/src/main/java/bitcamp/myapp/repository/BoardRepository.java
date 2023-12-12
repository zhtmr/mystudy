package bitcamp.myapp.repository;

import bitcamp.myapp.vo.Board;

// 게시글 데이터 보관
public class BoardRepository {

  private Board[] boards = new Board[3];
  private int length = 0;

  // 배열에 객체를 추가하는 부분을 메서드로 감춘다 --> 캡슐화
  public void add(Board board) {
    if (this.length == this.boards.length) {
      int oldSize = this.boards.length;
      int newSize = oldSize + (oldSize >> 1);

      Board[] arr = new Board[newSize];
      for (int i = 0; i < oldSize; i++) {
        arr[i] = this.boards[i];
      }

      this.boards = arr;
    }
    this.boards[this.length++] = board;
  }

  public Board remove(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }

    Board deleted = this.boards[index];

    for (int i = index; i < (this.length - 1); i++) {
      this.boards[i] = this.boards[i + 1];
    }
    this.boards[--this.length] = null;
    return deleted;
  }

  public Board get(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    return this.boards[index];
  }

  public Board[] toArray() {
    Board[] arr = new Board[this.length];
    for (int i = 0; i < this.length; i++) {
      arr[i] = this.boards[i];
    }
    return arr;
  }

  public Board set(int index, Board board) {
    if (index < 0 || index >= this.length) {
      return null;
    }

    Board old = this.boards[index];
    this.boards[index] = board;
    return old;
  }


}
