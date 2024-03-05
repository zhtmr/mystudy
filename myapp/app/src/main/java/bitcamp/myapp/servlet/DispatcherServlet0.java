package bitcamp.myapp.servlet;

import bitcamp.myapp.controller.*;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
//@WebServlet("/app/*")
public class DispatcherServlet0 extends HttpServlet {

  private List<Object> controllers = new ArrayList<>();

  @Override
  public void init() throws ServletException {
    ServletContext ctx = getServletContext();
    BoardDao boardDao = (BoardDao) ctx.getAttribute("boardDao");
    MemberDao memberDao = (MemberDao) ctx.getAttribute("memberDao");
    AssignmentDao assignmentDao = (AssignmentDao) ctx.getAttribute("assignmentDao");
    AttachedFileDao fileDao = (AttachedFileDao) ctx.getAttribute("fileDao");
    TransactionManager txManager = (TransactionManager) ctx.getAttribute("txManager");

    String boardUploadDir = getServletContext().getRealPath("/upload/board");
    String memberUploadDir = getServletContext().getRealPath("/upload");

    controllers.add(new HomeController());
    controllers.add(new AssignmentController(assignmentDao));
    controllers.add(new AuthController(memberDao));
    controllers.add(new BoardController(boardDao, fileDao, txManager, boardUploadDir));
    controllers.add(new MemberController(memberDao, memberUploadDir));

  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    try {
      // url 요청 처리할 핸들러 찾기
      RequestHandler requestHandler = findRequestHandler(req.getPathInfo());
      if (requestHandler == null) {
        throw new Exception(req.getPathInfo() + " 요청 페이지를 찾을 수 없습니다.");
      }

      String viewUrl = (String) requestHandler.handler.invoke(requestHandler.controller, req, res);

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

  private RequestHandler findRequestHandler(String path) {
    for (Object controller : controllers) {
      Method[] methods = controller.getClass().getDeclaredMethods();
      for (Method m : methods) {
        RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
        if (requestMapping != null && requestMapping.value().equals(path)) {
          return new RequestHandler(controller, m);
        }
      }
    }
    return null;
  }
}
