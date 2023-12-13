package bitcamp.myapp;


import bitcamp.myapp.handler.MenuGroup;
import bitcamp.myapp.handler.MenuItem;
import bitcamp.myapp.menu.HelpHandler;
import bitcamp.myapp.menu.assignment.AssignmentAddHandler;
import bitcamp.myapp.menu.assignment.AssignmentDeleteHandler;
import bitcamp.myapp.menu.assignment.AssignmentListHandler;
import bitcamp.myapp.menu.assignment.AssignmentModifyHandler;
import bitcamp.myapp.menu.assignment.AssignmentViewHandler;
import bitcamp.myapp.menu.board.BoardAddHandler;
import bitcamp.myapp.menu.board.BoardDeleteHandler;
import bitcamp.myapp.menu.board.BoardListHandler;
import bitcamp.myapp.menu.board.BoardModifyHandler;
import bitcamp.myapp.menu.board.BoardViewHandler;
import bitcamp.myapp.menu.member.MemberAddHandler;
import bitcamp.myapp.menu.member.MemberDeleteHandler;
import bitcamp.myapp.menu.member.MemberListHandler;
import bitcamp.myapp.menu.member.MemberModifyHandler;
import bitcamp.myapp.menu.member.MemberViewHandler;
import bitcamp.myapp.vo.Assignment;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Prompt;
import java.util.ArrayList;

public class App {

  public static void main(String[] args) throws Exception {
    Prompt prompt = new Prompt(System.in);
//    new MainMenu(prompt).execute();
    ArrayList<Assignment> assignmentRepository = new ArrayList<>();
    ArrayList<Board> boardRepository = new ArrayList<>();
    ArrayList<Member> memberRepository = new ArrayList<>();
    ArrayList<Board> greetingRepository = new ArrayList<>();

    MenuGroup mainMenu = new MenuGroup("메인");

    MenuGroup assignmentMenu = new MenuGroup("과제");
    assignmentMenu.add(new MenuItem("등록", new AssignmentAddHandler(prompt, assignmentRepository)));
    assignmentMenu.add(new MenuItem("조회", new AssignmentViewHandler(prompt, assignmentRepository)));
    assignmentMenu.add(
        new MenuItem("변경", new AssignmentModifyHandler(prompt, assignmentRepository)));
    assignmentMenu.add(
        new MenuItem("삭제", new AssignmentDeleteHandler(prompt, assignmentRepository)));
    assignmentMenu.add(new MenuItem("목록", new AssignmentListHandler(assignmentRepository)));
    mainMenu.add(assignmentMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new MenuItem("등록", new BoardAddHandler(boardRepository, prompt)));
    boardMenu.add(new MenuItem("조회", new BoardViewHandler(boardRepository, prompt)));
    boardMenu.add(new MenuItem("변경", new BoardModifyHandler(boardRepository, prompt)));
    boardMenu.add(new MenuItem("삭제", new BoardDeleteHandler(boardRepository, prompt)));
    boardMenu.add(new MenuItem("목록", new BoardListHandler(boardRepository)));
    mainMenu.add(boardMenu);

    MenuGroup memberMenu = new MenuGroup("회원");
    memberMenu.add(new MenuItem("등록", new MemberAddHandler(memberRepository, prompt)));
    memberMenu.add(new MenuItem("조회", new MemberViewHandler(memberRepository, prompt)));
    memberMenu.add(new MenuItem("변경", new MemberModifyHandler(memberRepository, prompt)));
    memberMenu.add(new MenuItem("삭제", new MemberDeleteHandler(memberRepository, prompt)));
    memberMenu.add(new MenuItem("목록", new MemberListHandler(memberRepository)));
    mainMenu.add(memberMenu);

    MenuGroup greetingMenu = new MenuGroup("가입인사");
    greetingMenu.add(new MenuItem("등록", new BoardAddHandler(greetingRepository, prompt)));
    greetingMenu.add(new MenuItem("조회", new BoardViewHandler(greetingRepository, prompt)));
    greetingMenu.add(new MenuItem("변경", new BoardModifyHandler(greetingRepository, prompt)));
    greetingMenu.add(new MenuItem("삭제", new BoardDeleteHandler(greetingRepository, prompt)));
    greetingMenu.add(new MenuItem("목록", new BoardListHandler(greetingRepository)));
    mainMenu.add(greetingMenu);

    mainMenu.add(new MenuItem("도움말", new HelpHandler()));
    mainMenu.execute(prompt);
    prompt.close();
  }
}
