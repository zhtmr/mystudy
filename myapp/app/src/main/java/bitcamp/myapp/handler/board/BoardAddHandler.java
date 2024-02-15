package bitcamp.myapp.handler.board;

import bitcamp.menu.AbstractMenuHandler;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Prompt;
import bitcamp.util.TransactionManager;

import java.util.ArrayList;

public class BoardAddHandler extends AbstractMenuHandler {

  private BoardDao boardDao;
  private AttachedFileDao fileDao;
  private TransactionManager txManager;

  public BoardAddHandler(TransactionManager txManager, BoardDao boardDao, AttachedFileDao fileDao) {
    this.fileDao = fileDao;
    this.txManager = txManager;
    this.boardDao = boardDao;
  }

  @Override
  protected void action(Prompt prompt) {
    try {
      Member loginUser = (Member) prompt.getSession().getAttribute("loginUser");
      if (loginUser != null) {
        Board board = new Board();
        board.setTitle(prompt.input("제목? "));
        board.setContent(prompt.input("내용? "));
        board.setWriter(loginUser);

        ArrayList<AttachedFile> files = new ArrayList<>();
        while (true) {
          String filePath = prompt.input("파일?(종료: 그냥 엔터) ");
          if (filePath.isEmpty()) {
            break;
          }
          files.add(new AttachedFile().filePath(filePath));
        }

        txManager.begin();

        boardDao.add(board);
        if (!files.isEmpty()) {
          for (AttachedFile file : files) {
            file.setBoardNo(board.getNo());
          }
          fileDao.addAll(files);
        }

        txManager.commit();
      } else {
        prompt.println("로그인을 해주세요.");
      }
    } catch (Exception e) {
      try {
        txManager.rollback();
      } catch (Exception e2) {
      }
      prompt.input("게시글 등록 오류!");
    }
  }
}
