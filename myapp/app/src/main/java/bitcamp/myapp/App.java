package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.myapp.handler.HelpHandler;
import bitcamp.myapp.handler.assignment.*;
import bitcamp.myapp.handler.board.*;
import bitcamp.myapp.handler.member.*;
import bitcamp.myapp.vo.Assignment;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Prompt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {
  Prompt prompt = new Prompt(System.in);
  List<Board> boardRepository = new LinkedList<>();
  List<Assignment> assignmentRepository = new LinkedList<>();
  List<Member> memberRepository = new ArrayList<>();
  List<Board> greetingRepository = new ArrayList<>();
  MenuGroup mainMenu;

  App() {
    prepareMenu();
    loadAssignment();
    loadMember();
    loadBoard();
    loadGreeting();
  }

  public static void main(String[] args) throws Exception {
    new App().run();
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
    saveAssignment();
    saveMember();
    saveBoard();
    saveGreeting();
  }

  void prepareMenu() {
    mainMenu = MenuGroup.getInstance("메인");

    MenuGroup assignmentMenu = mainMenu.addGroup("과제");
    assignmentMenu.addItem("등록", new AssignmentAddHandler(assignmentRepository, prompt));
    assignmentMenu.addItem("조회", new AssignmentViewHandler(assignmentRepository, prompt));
    assignmentMenu.addItem("변경", new AssignmentModifyHandler(assignmentRepository, prompt));
    assignmentMenu.addItem("삭제", new AssignmentDeleteHandler(assignmentRepository, prompt));
    assignmentMenu.addItem("목록", new AssignmentListHandler(assignmentRepository, prompt));

    MenuGroup boardMenu = mainMenu.addGroup("게시글");
    boardMenu.addItem("등록", new BoardAddHandler(boardRepository, prompt));
    boardMenu.addItem("조회", new BoardViewHandler(boardRepository, prompt));
    boardMenu.addItem("변경", new BoardModifyHandler(boardRepository, prompt));
    boardMenu.addItem("삭제", new BoardDeleteHandler(boardRepository, prompt));
    boardMenu.addItem("목록", new BoardListHandler(boardRepository, prompt));

    MenuGroup memberMenu = mainMenu.addGroup("회원");
    memberMenu.addItem("등록", new MemberAddHandler(memberRepository, prompt));
    memberMenu.addItem("조회", new MemberViewHandler(memberRepository, prompt));
    memberMenu.addItem("변경", new MemberModifyHandler(memberRepository, prompt));
    memberMenu.addItem("삭제", new MemberDeleteHandler(memberRepository, prompt));
    memberMenu.addItem("목록", new MemberListHandler(memberRepository, prompt));

    MenuGroup greetingMenu = mainMenu.addGroup("가입인사");
    greetingMenu.addItem("등록", new BoardAddHandler(greetingRepository, prompt));
    greetingMenu.addItem("조회", new BoardViewHandler(greetingRepository, prompt));
    greetingMenu.addItem("변경", new BoardModifyHandler(greetingRepository, prompt));
    greetingMenu.addItem("삭제", new BoardDeleteHandler(greetingRepository, prompt));
    greetingMenu.addItem("목록", new BoardListHandler(greetingRepository, prompt));

    mainMenu.addItem("도움말", new HelpHandler(prompt));
  }

  void loadAssignment() {
    try (FileInputStream in = new FileInputStream("assignment.data")) {
      byte[] bytes = new byte[60000];
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        String title = new String(bytes, 0, len, StandardCharsets.UTF_8);

        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        String content = new String(bytes, 0, len, StandardCharsets.UTF_8);

        // 날짜는 10 byte 만 읽는다
        in.read(bytes, 0, 10);
        Date deadline = Date.valueOf(new String(bytes, 0, 10, StandardCharsets.UTF_8));

        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setContent(content);
        assignment.setDeadline(deadline);

        assignmentRepository.add(assignment);
      }
    } catch (Exception e) {
      System.out.println("과제 데이터 로딩 중 오류 발생!");
      e.printStackTrace();
    }

  }

  void saveAssignment() {
    try (FileOutputStream out = new FileOutputStream("assignment.data")) {

      // 저장할 데이터 갯수를 2바이트로 출력한다.
      out.write(assignmentRepository.size() >> 8);
      out.write(assignmentRepository.size());

      for (Assignment assignment : assignmentRepository) {
        byte[] bytes = assignment.getTitle().getBytes(StandardCharsets.UTF_8);
        // byte갯수를 2바이트로 출력(규칙 정하기)
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = assignment.getContent().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        out.write(assignment.getDeadline().toString().getBytes(StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      System.out.println("과제 데이터 저장 중 오류 발생!");
      e.printStackTrace();
    }

  }

  void saveMember() {
    try (FileOutputStream out = new FileOutputStream("member.data")) {
      out.write(memberRepository.size() >> 8);
      out.write(memberRepository.size());

      for (Member member : memberRepository) {
        byte[] bytes = member.getName().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = member.getEmail().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = member.getPassword().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // 1바이트씩 이동시켜서 하나씩 출력
        // write()는 int 값을 줘야한다 -> long에서 앞 4바이트 버리고 int 로 캐스팅해도 상관없다. 맨뒤 1바이트씩만 쓰기 때문
        long date = member.getCreatedDate().getTime();
        out.write((int) (date >> 56));
        out.write((int) (date >> 48));
        out.write((int) (date >> 40));
        out.write((int) (date >> 32));
        out.write((int) (date >> 24));
        out.write((int) (date >> 16));
        out.write((int) (date >> 8));
        out.write((int) (date));
      }
    } catch (Exception e) {
      System.out.println("회원 데이터 저장 중 오류 발생!");

    }
  }

  void loadMember() {
    try (FileInputStream in = new FileInputStream("member.data")) {
      byte[] bytes = new byte[60000]; // 임시 버퍼
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        Member member = new Member();

        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setName(new String(bytes, 0, len, StandardCharsets.UTF_8));

        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setEmail(new String(bytes, 0, len, StandardCharsets.UTF_8));

        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setPassword(new String(bytes, 0, len, StandardCharsets.UTF_8));

        long date =
            ((long) in.read() << 56) | ((long) in.read() << 48) | ((long) in.read() << 40) | ((long) in.read() << 32) | ((long) in.read() << 24) | ((long) in.read() << 16) | ((long) in.read() << 8) | in.read();

        member.setCreatedDate(new java.util.Date(date));

        memberRepository.add(member);
      }
    } catch (Exception e) {
      System.out.println("회원 데이터 로딩 중 오류 발생!");
      e.printStackTrace();
    }
  }

  void saveGreeting() {
    try (FileOutputStream out = new FileOutputStream("greeting.data")) {
      out.write(greetingRepository.size() >> 8);
      out.write(greetingRepository.size());

      for (Board board : greetingRepository) {
        byte[] bytes = board.getTitle().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getContent().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getWriter().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        long date = board.getCreatedDate().getTime();
        out.write((int) (date >> 56));
        out.write((int) (date >> 48));
        out.write((int) (date >> 40));
        out.write((int) (date >> 32));
        out.write((int) (date >> 24));
        out.write((int) (date >> 16));
        out.write((int) (date >> 8));
        out.write((int) date);
      }
    } catch (Exception e) {
      System.out.println("가입인사 데이터 저장 중 오류 발생!");
    }
  }

  void loadGreeting() {
    try (FileInputStream in = new FileInputStream("greeting.data")) {
      byte[] bytes = new byte[60000];
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        String title = new String(bytes, 0, len, StandardCharsets.UTF_8);

        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        String content = new String(bytes, 0, len, StandardCharsets.UTF_8);

        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        String writer = new String(bytes, 0, len, StandardCharsets.UTF_8);

        long date =
            ((long) in.read() << 56) | ((long) in.read() << 48) | ((long) in.read() << 40) | ((long) in.read() << 32) | ((long) in.read() << 24) | ((long) in.read() << 16) | ((long) in.read() << 8) | in.read();

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(writer);
        board.setCreatedDate(new java.util.Date(date));

        greetingRepository.add(board);
      }
    } catch (Exception e) {
      System.out.println("가입인사 데이터 로딩 중 오류 발생!");
    }
  }

  void saveBoard() {
    try (FileOutputStream out = new FileOutputStream("board.data")) {
      out.write(boardRepository.size() >> 8);
      out.write(boardRepository.size());

      for (Board board : boardRepository) {
        byte[] bytes = board.getTitle().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getContent().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getWriter().getBytes(StandardCharsets.UTF_8);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        long date = board.getCreatedDate().getTime();
        out.write((int) (date >> 56));
        out.write((int) (date >> 48));
        out.write((int) (date >> 40));
        out.write((int) (date >> 32));
        out.write((int) (date >> 24));
        out.write((int) (date >> 16));
        out.write((int) (date >> 8));
        out.write((int) date);
      }

    } catch (Exception e) {
      System.out.println("게시글 데이터 저장 중 오류 발생!");
    }
  }

  void loadBoard() {
    try (FileInputStream in = new FileInputStream("board.data")) {
      byte[] bytes = new byte[60000];
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        Board board = new Board();

        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        board.setTitle(new String(bytes, 0, len, StandardCharsets.UTF_8));

        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        board.setContent(new String(bytes, 0, len, StandardCharsets.UTF_8));

        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        board.setWriter(new String(bytes, 0, len, StandardCharsets.UTF_8));

        long date =
            ((long) in.read() << 56) | ((long) in.read() << 48) | ((long) in.read() << 40) | ((long) in.read() << 32) | ((long) in.read() << 24) | ((long) in.read() << 16) | ((long) in.read() << 8) | in.read();
        board.setCreatedDate(new java.util.Date(date));
        boardRepository.add(board);
      }
    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
