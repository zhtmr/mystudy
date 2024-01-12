package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.json.AssignmentDaoImpl;
import bitcamp.myapp.dao.json.BoardDaoImpl;
import bitcamp.myapp.dao.json.MemberDaoImpl;
import bitcamp.myapp.handler.HelpHandler;
import bitcamp.myapp.handler.assignment.*;
import bitcamp.myapp.handler.board.*;
import bitcamp.myapp.handler.member.*;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientApp {
  Prompt prompt = new Prompt(System.in);
  MemberDao memberDao = new MemberDaoImpl("member.json");
  AssignmentDao assignmentDao = new AssignmentDaoImpl("assignment.json");
  BoardDao boardDao = new BoardDaoImpl("board.json");
  BoardDao greetingDao = new BoardDaoImpl("greeting.json");
  MenuGroup mainMenu;

  ClientApp() {

    prepareMenu();
  }

  public static void main(String[] args) {
    System.out.println("[과제관리 시스템]");

    try {

      // 1) 서버와 연결한 후 연결 정보 준비
      // => new Socket(서버주소, 포트번호)
      //    - 서버 주소: IP 주소, 도메인명
      //    - 포트 번호: 서버 포트 번호
      // => 로컬 컴퓨터를 가리키는
      //    - IP 주소: 127.0.0.1
      //    - 도메인명: localhost
      System.out.println("서버 연결 중...");
      Socket socket = new Socket("localhost", 8888);
      System.out.println("서버와 연결 되었음!");

      DataInputStream in = new DataInputStream(socket.getInputStream());
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());
      System.out.println("입출력 준비 완료!");

      out.writeUTF("board");
      out.writeUTF("findAll");
      out.writeUTF("");
      System.out.println("서버에 데이터 전송완료!");

      String response = in.readUTF();
      ArrayList<Board> list =
          (ArrayList<Board>) new GsonBuilder().setDateFormat("yyyy-MM-dd").create()
              .fromJson(response, TypeToken.getParameterized(ArrayList.class, Board.class));
      
      for (Board board : list) {
        System.out.println(board);
      }

    } catch (Exception e) {
      System.out.println("통신 오류!");
      e.printStackTrace();
    }
    //    new ClientApp().run();
  }

  void run() {
    while (true) { // 예외가 발생해도 App을 종료하지 않음.
      try {
        mainMenu.execute(prompt);
        prompt.close();
        break;
      } catch (Exception e) {
        System.out.println("main() 예외 발생");
      }
    }
  }

  void prepareMenu() {
    mainMenu = MenuGroup.getInstance("메인");

    MenuGroup assignmentMenu = mainMenu.addGroup("과제");
    assignmentMenu.addItem("등록", new AssignmentAddHandler(assignmentDao, prompt));
    assignmentMenu.addItem("조회", new AssignmentViewHandler(assignmentDao, prompt));
    assignmentMenu.addItem("변경", new AssignmentModifyHandler(assignmentDao, prompt));
    assignmentMenu.addItem("삭제", new AssignmentDeleteHandler(assignmentDao, prompt));
    assignmentMenu.addItem("목록", new AssignmentListHandler(assignmentDao, prompt));

    MenuGroup boardMenu = mainMenu.addGroup("게시글");
    boardMenu.addItem("등록", new BoardAddHandler(boardDao, prompt));
    boardMenu.addItem("조회", new BoardViewHandler(boardDao, prompt));
    boardMenu.addItem("변경", new BoardModifyHandler(boardDao, prompt));
    boardMenu.addItem("삭제", new BoardDeleteHandler(boardDao, prompt));
    boardMenu.addItem("목록", new BoardListHandler(boardDao, prompt));

    MenuGroup memberMenu = mainMenu.addGroup("회원");
    memberMenu.addItem("등록", new MemberAddHandler(memberDao, prompt));
    memberMenu.addItem("조회", new MemberViewHandler(memberDao, prompt));
    memberMenu.addItem("변경", new MemberModifyHandler(memberDao, prompt));
    memberMenu.addItem("삭제", new MemberDeleteHandler(memberDao, prompt));
    memberMenu.addItem("목록", new MemberListHandler(memberDao, prompt));

    MenuGroup greetingMenu = mainMenu.addGroup("가입인사");
    greetingMenu.addItem("등록", new BoardAddHandler(greetingDao, prompt));
    greetingMenu.addItem("조회", new BoardViewHandler(greetingDao, prompt));
    greetingMenu.addItem("변경", new BoardModifyHandler(greetingDao, prompt));
    greetingMenu.addItem("삭제", new BoardDeleteHandler(greetingDao, prompt));
    greetingMenu.addItem("목록", new BoardListHandler(greetingDao, prompt));

    mainMenu.addItem("도움말", new HelpHandler(prompt));
  }

}
