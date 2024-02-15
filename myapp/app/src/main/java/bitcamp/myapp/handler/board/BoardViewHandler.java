package bitcamp.myapp.handler.board;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

import java.util.List;

public class BoardViewHandler extends AbstractMenuHandler {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;

  public BoardViewHandler(BoardDao boardDao, AttachedFileDao fileDao) {
    this.fileDao = fileDao;
    this.boardDao = boardDao;
  }

  @Override
  protected void action(Prompt prompt) {
    try {
      int no = prompt.inputInt("번호? ");
      Board board = boardDao.findBy(no);
      if (board == null) {
        prompt.println("게시글 번호가 유효하지 않습니다.");
        return;
      }
      List<AttachedFile> files = fileDao.findAllByBoardNo(no);
      prompt.printf("번호: %d\n", board.getNo());
      prompt.printf("제목: %s\n", board.getTitle());
      prompt.printf("내용: %s\n", board.getContent());
      prompt.printf("작성자: %s\n", board.getWriter().getName());
      prompt.printf("작성일: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS\n", board.getCreatedDate());
      prompt.printf("첨부파일:\n");

      for (AttachedFile file : files) {
        prompt.printf("  - %s\n", file.getFilePath());
      }

    } catch (Exception e) {
      prompt.println("게시글 상세 오류!");
    }
  }
}
