package bitcamp.myapp.servlet;

import bitcamp.myapp.controller.HomeController;
import bitcamp.myapp.controller.RequestMapping;
import bitcamp.myapp.controller.assignment.*;
import bitcamp.myapp.controller.auth.LoginController;
import bitcamp.myapp.controller.auth.LogoutController;
import bitcamp.myapp.controller.board.*;
import bitcamp.myapp.controller.member.*;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

  private Map<String, Object> controllerMap = new HashMap<>();

  @Override
  public void init() throws ServletException {
    ServletContext ctx = getServletContext();
    BoardDao boardDao = (BoardDao) ctx.getAttribute("boardDao");
    MemberDao memberDao = (MemberDao) ctx.getAttribute("memberDao");
    AssignmentDao assignmentDao = (AssignmentDao) ctx.getAttribute("assignmentDao");
    AttachedFileDao fileDao = (AttachedFileDao) ctx.getAttribute("fileDao");
    TransactionManager txManager = (TransactionManager) ctx.getAttribute("txManager");

    controllerMap.put("/home", new HomeController());

    String memberUploadDir = getServletContext().getRealPath("/upload");
    controllerMap.put("/member/list", new MemberListController(memberDao));
    controllerMap.put("/member/view", new MemberViewController(memberDao));
    controllerMap.put("/member/add", new MemberAddController(memberDao, memberUploadDir));
    controllerMap.put("/member/update", new MemberUpdateController(memberDao, memberUploadDir));
    controllerMap.put("/member/delete", new MemberDeleteController(memberDao, memberUploadDir));

    controllerMap.put("/assignment/list", new AssignmentListController(assignmentDao));
    controllerMap.put("/assignment/view", new AssignmentViewController(assignmentDao));
    controllerMap.put("/assignment/add", new AssignmentAddController(assignmentDao));
    controllerMap.put("/assignment/update", new AssignmentUpdateController(assignmentDao));
    controllerMap.put("/assignment/delete", new AssignmentDeleteController(assignmentDao));

    controllerMap.put("/auth/login", new LoginController(memberDao));
    controllerMap.put("/auth/logout", new LogoutController());

    String boardUploadDir = getServletContext().getRealPath("/upload/board");
    controllerMap.put("/board/list", new BoardListController(boardDao));
    controllerMap.put("/board/view", new BoardViewController(boardDao, fileDao));
    controllerMap.put("/board/add", new BoardAddController(boardDao, fileDao, txManager, boardUploadDir));
    controllerMap.put("/board/update", new BoardUpdateController(boardDao, txManager, fileDao, boardUploadDir));
    controllerMap.put("/board/delete", new BoardDeleteController(boardDao, fileDao, boardUploadDir, txManager));
    controllerMap.put("/board/file/delete", new BoardFileDeleteController(boardDao, fileDao, boardUploadDir));

  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    // url 에서 요청한 페이지 컨트롤러를 실행한다.
    Object controller = controllerMap.get(req.getPathInfo());
    if (controller == null) {
      throw new ServletException(req.getPathInfo() + " 요청 페이지를 찾을 수 없습니다.");
    }

    try {
      Method requestHandler = findRequestHandler(controller);
      if (requestHandler == null) {
        throw new Exception(req.getPathInfo() + " 요청 페이지를 찾을 수 없습니다.");
      }

      String viewUrl = (String) requestHandler.invoke(controller, req, res);

      // 페이지 컨트롤러가 알려준 jsp 로 포워딩 한다.
      if (viewUrl.startsWith("redirect:")) {
        res.sendRedirect(viewUrl.substring(9));
      } else {
        req.getRequestDispatcher(viewUrl).forward(req, res);
      }

    } catch (Exception e) {
      req.setAttribute("message", req.getPathInfo() + "실행 오류!");

      StringWriter writer = new StringWriter();
      PrintWriter out = new PrintWriter(writer);
      e.printStackTrace(out);
      req.setAttribute("detail", writer.toString());

      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }

  private Method findRequestHandler(Object controller) {
    Method[] methods = controller.getClass().getDeclaredMethods();
    for (Method m : methods) {
      RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
      if (requestMapping != null) {
        return m;
      }
    }
    return null;
  }
}
