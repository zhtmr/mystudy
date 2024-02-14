package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.mysql.AssignmentDaoImpl;
import bitcamp.myapp.dao.mysql.AttachedFileDaoImpl;
import bitcamp.myapp.dao.mysql.BoardDaoImpl;
import bitcamp.myapp.dao.mysql.MemberDaoImpl;
import bitcamp.myapp.handler.AboutHandler;
import bitcamp.myapp.handler.HelpHandler;
import bitcamp.myapp.handler.assignment.*;
import bitcamp.myapp.handler.board.*;
import bitcamp.myapp.handler.member.*;
import bitcamp.util.Prompt;
import bitcamp.util.DBConnectionPool;
import bitcamp.util.TransactionManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
  ExecutorService executorService = Executors.newCachedThreadPool();

  TransactionManager txManager;
  DBConnectionPool connectionPool;
  BoardDao boardDao;
  BoardDao greetingDao;
  AssignmentDao assignmentDao;
  MemberDao memberDao;
  AttachedFileDao fileDao;
  MenuGroup mainMenu;

  ServerApp() {
    prepareDatabase();
    prepareMenu();
  }

  public static void main(String[] args) {
    System.out.println("[과제관리 시스템 서버 실행!]");
    new ServerApp().run();
  }

  void run() {
    try (ServerSocket serverSocket = new ServerSocket(8888)) {

      while (true) {
        Socket socket = serverSocket.accept();
        executorService.execute(() -> processRequest(socket));
      }

    } catch (Exception e) {
      System.out.println("서버 소켓 생성 오류!");
      e.printStackTrace();
    } finally {
      connectionPool.closeAll();
    }
  }

  void processRequest(Socket socket) {
    try (Socket s = socket;
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        DataInputStream in = new DataInputStream(s.getInputStream());
        Prompt prompt = new Prompt(in, out)) {

      while (true) {
        try {
          mainMenu.execute(prompt);
          prompt.print("[[quit!]]");
          prompt.end();
          break;
        } catch (NoSuchElementException e) {
          System.out.println("main() 예외 발생");
          e.printStackTrace();
        }
      }

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();
    } finally {
//      threadConnection.remove();
    }
  }


  void prepareDatabase() {
    try {
      // JVM 이 JDBC 드라이버 파일(.jar)에 설정된대로 자동으로 처리한다.
      //      Driver driver = new com.mysql.jdbc.Driver();
      //      DriverManager.registerDriver(driver);
      //      Connection con = DriverManager.getConnection(
      //          //              "jdbc:log4jbdc:mysql://127.0.0.1/studydb", "study", "Bitcamp!@#123");
      //          "jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123");

      connectionPool =
          new DBConnectionPool("jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb", "study",
              "Bitcamp!@#123");
      txManager = new TransactionManager(connectionPool);

      boardDao = new BoardDaoImpl(connectionPool, 1);
      greetingDao = new BoardDaoImpl(connectionPool, 2);
      assignmentDao = new AssignmentDaoImpl(connectionPool);
      memberDao = new MemberDaoImpl(connectionPool);
      fileDao = new AttachedFileDaoImpl(connectionPool);

    } catch (Exception e) {
      System.out.println("통신 오류!");
      e.printStackTrace();
    }
  }

  void prepareMenu() {
    mainMenu = MenuGroup.getInstance("메인");

    MenuGroup assignmentMenu = mainMenu.addGroup("과제");
    assignmentMenu.addItem("등록", new AssignmentAddHandler(txManager, assignmentDao));
    assignmentMenu.addItem("조회", new AssignmentViewHandler(assignmentDao));
    assignmentMenu.addItem("변경", new AssignmentModifyHandler(assignmentDao));
    assignmentMenu.addItem("삭제", new AssignmentDeleteHandler(assignmentDao));
    assignmentMenu.addItem("목록", new AssignmentListHandler(assignmentDao));

    MenuGroup boardMenu = mainMenu.addGroup("게시글");
    boardMenu.addItem("등록", new BoardAddHandler(txManager, boardDao, fileDao));
    boardMenu.addItem("조회", new BoardViewHandler(boardDao, fileDao));
    boardMenu.addItem("변경", new BoardModifyHandler(txManager, boardDao, fileDao));
    boardMenu.addItem("삭제", new BoardDeleteHandler(txManager, boardDao, fileDao));
    boardMenu.addItem("목록", new BoardListHandler(boardDao));

    MenuGroup memberMenu = mainMenu.addGroup("회원");
    memberMenu.addItem("등록", new MemberAddHandler(memberDao));
    memberMenu.addItem("조회", new MemberViewHandler(memberDao));
    memberMenu.addItem("변경", new MemberModifyHandler(memberDao));
    memberMenu.addItem("삭제", new MemberDeleteHandler(memberDao));
    memberMenu.addItem("목록", new MemberListHandler(memberDao));

    MenuGroup greetingMenu = mainMenu.addGroup("가입인사");
    greetingMenu.addItem("등록", new BoardAddHandler(txManager, greetingDao, fileDao));
    greetingMenu.addItem("조회", new BoardViewHandler(greetingDao, fileDao));
    greetingMenu.addItem("변경", new BoardModifyHandler (txManager, greetingDao, fileDao));
    greetingMenu.addItem("삭제", new BoardDeleteHandler(txManager, greetingDao, fileDao));
    greetingMenu.addItem("목록", new BoardListHandler(greetingDao));

    mainMenu.addItem("도움말", new HelpHandler());
    mainMenu.addItem("...대하여", new AboutHandler());
  }

}
