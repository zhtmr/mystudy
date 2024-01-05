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

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {
  Prompt prompt = new Prompt(System.in);
  List<Board> boardRepository = new ArrayList<>();
  List<Assignment> assignmentRepository = new LinkedList<>();
  List<Member> memberRepository = new ArrayList<>();
  List<Board> greetingRepository = new LinkedList<>();
  MenuGroup mainMenu;

  App() {
    assignmentRepository = loadData("assignment.data");
    memberRepository = loadData("member.data");
    boardRepository = loadData("board.data");
    greetingRepository = loadData("greeting.data");

    prepareMenu();  // 기존에 이 메소드를 제일 먼저 실행했을땐 App 클래스 필드에서 할당한 빈 리스트 구현체를 넘겨준 뒤, loadAssignment() 호출 시 해당 repository 에 addAll() 등으로 데이터를 넣어줬었다.
    // 그러나 수정된 코드에서는 실제로 데이터를 넣어주는 시점이 다르기 때문에 예외가 발생할 수 있다.
  }

  public static void main(String[] args) {
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
    saveData("assignment.data", assignmentRepository);
    saveData("member.data", memberRepository);
    saveData("board.data", boardRepository);
    saveData("greeting.data", greetingRepository);
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


  /** 리턴 타입에 따라 다른 리스트를 반환할 수 있다. */
  <E> List<E> loadData(String filepath) {
    try (ObjectInputStream in = new ObjectInputStream(
        new BufferedInputStream(new FileInputStream(filepath)))) {

      return (List<E>) in.readObject();
    } catch (Exception e) {
      System.out.printf("%s 로딩 중 오류 발생!\n", filepath);
      e.printStackTrace();
    }
    return new ArrayList<>(); // 예외 발생시 빈 리스트 반환.
  }

  void saveData(String filepath, List<?> dataList) {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new BufferedOutputStream(new FileOutputStream(filepath)))) {

      out.writeObject(dataList);

    } catch (Exception e) {
      System.out.printf("%s 저장 중 오류 발생!\n", filepath);
    }
  }
}
