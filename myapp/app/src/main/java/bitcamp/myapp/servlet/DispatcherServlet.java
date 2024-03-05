package bitcamp.myapp.servlet;

import bitcamp.myapp.controller.*;
import bitcamp.myapp.dao.AssignmentDao;
import bitcamp.myapp.dao.AttachedFileDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.util.TransactionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

  private Map<String, RequestHandler> requestHandlerMap = new HashMap<>();
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

    prepareRequestHandlers(controllers);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    try {
      // url 요청 처리할 핸들러 찾기
      RequestHandler requestHandler = requestHandlerMap.get(req.getPathInfo());
      if (requestHandler == null) {
        throw new Exception(req.getPathInfo() + " 요청 페이지를 찾을 수 없습니다.");
      }

      Object[] args = prepareRequestHandlerArguments(requestHandler.handler, req, res);

      String viewUrl = (String) requestHandler.handler.invoke(requestHandler.controller, args);

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

  private void prepareRequestHandlers(List<Object> controllers) {
    for (Object controller : controllers) {
      Method[] methods = controller.getClass().getDeclaredMethods();
      for (Method m : methods) {
        RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
          requestHandlerMap.put(requestMapping.value(), new RequestHandler(controller, m));
        }
      }
    }
  }

  private Object[] prepareRequestHandlerArguments(Method handler, HttpServletRequest request, HttpServletResponse response) {
    Parameter[] params = handler.getParameters();

    // 파라미터로 전달할 값을 담을 배열
    Object[] args = new Object[params.length];

    // 파라미터를 배열에 담는다.
    for (int i = 0; i < params.length; i++) {
      Parameter param = params[i];
      if (param.getType() == HttpServletRequest.class || param.getType() == ServletRequest.class) {
        args[i] = request;
      } else if (param.getType() == HttpServletResponse.class || param.getType() == ServletResponse.class) {
        args[i] = response;
      } else {
        RequestParam requestParam = param.getAnnotation(RequestParam.class);
        String paramName = requestParam.value();
        String paramValue = request.getParameter(paramName);
        if (param.getType() == byte.class) {
          args[i] = Byte.parseByte(paramValue);
        } else if (param.getType() == short.class) {
          args[i] = Short.parseShort(paramValue);
        } else if (param.getType() == int.class) {
          args[i] = Integer.parseInt(paramValue);
        } else if (param.getType() == long.class) {
          args[i] = Long.parseLong(paramValue);
        } else if (param.getType() == float.class) {
          args[i] = Float.parseFloat(paramValue);
        } else if (param.getType() == double.class) {
          args[i] = Double.parseDouble(paramValue);
        } else if (param.getType() == boolean.class) {
          args[i] = Boolean.parseBoolean(paramValue);
        } else if (param.getType() == char.class) {
          args[i] = paramValue.charAt(0);
        } else if (param.getType() == Date.class) {
          args[i] = Date.valueOf(paramValue);
        } else {
          args[i] = paramValue;
        }
      }
    }
    return args;
  }

}
