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
import java.lang.reflect.Constructor;
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

  private Object[] prepareRequestHandlerArguments(Method handler, HttpServletRequest request, HttpServletResponse response) throws Exception{
    Parameter[] methodParams = handler.getParameters();

    // 파라미터로 전달할 값을 담을 배열
    Object[] args = new Object[methodParams.length];

    // 파라미터를 배열에 담는다.
    for (int i = 0; i < methodParams.length; i++) {
      Parameter methodParam = methodParams[i];
      if (methodParam.getType() == HttpServletRequest.class || methodParam.getType() == ServletRequest.class) {
        args[i] = request;
      } else if (methodParam.getType() == HttpServletResponse.class || methodParam.getType() == ServletResponse.class) {
        args[i] = response;
      } else {
        RequestParam requestParam = methodParam.getAnnotation(RequestParam.class);
        if (requestParam != null) { // @RequestParam 어노테이션이 붙은 경우
          String requestParamName = requestParam.value();
          String requestParamValue = request.getParameter(requestParamName);
          args[i] = valueOf(requestParamValue, methodParam.getType());
        } else {  // 파라미터 타입이 도메인 클래스인 경우
          args[i] = createValueObject(methodParam.getType(), request);
        }
      }
    }
    return args;
  }

  private Object valueOf(String stringValue, Class<?> type) {
    if (type == byte.class) {
      return Byte.parseByte(stringValue);
    } else if (type == short.class) {
      return Short.parseShort(stringValue);
    } else if (type == int.class) {
      return Integer.parseInt(stringValue);
    } else if (type == long.class) {
      return Long.parseLong(stringValue);
    } else if (type == float.class) {
      return Float.parseFloat(stringValue);
    } else if (type == double.class) {
      return Double.parseDouble(stringValue);
    } else if (type == boolean.class) {
      return Boolean.parseBoolean(stringValue);
    } else if (type == char.class) {
      return stringValue.charAt(0);
    } else if (type == Date.class) {
      return Date.valueOf(stringValue);
    } else if (type == String.class) {
      return stringValue;
    }
    return null;
  }

  // 도메인 객체 생성 후 값을 담아서 리턴
  private Object createValueObject(Class<?> type, HttpServletRequest request)
      throws Exception {
    // 1. 도메인 클래스 생성자
    Constructor<?> constructor = type.getConstructor();

    // 2. 도메인 객체 생성
    Object obj = constructor.newInstance();

    // 3. 도메인 클래스 메소드 정보
    Method[] methods = type.getDeclaredMethods();

    // 4. setter
    for (Method setter : methods) {
      if (!setter.getName().startsWith("set")) {
        continue;
      }
      // 5. setter 메소드에서 프로퍼티 이름 추출
      String propName =
          Character.toLowerCase(setter.getName().charAt(3)) + setter.getName().substring(4);
      // 6. 프로퍼티 이름으로 넘어온 요청 파라미터 값을 꺼낸다
      String reqParamValue = request.getParameter(propName);

      // 7. 도메인 객체의 프로퍼티 이름과 일치하는 요청 파라미터 값이 있다면 객체에 저장한다.
      if (reqParamValue != null) {
        Class<?> setterParameterType = setter.getParameters()[0].getType();
        setter.invoke(obj, valueOf(reqParamValue, setterParameterType));  // => setFirstName("길동");
      }
    }
    return obj;
  }
}
