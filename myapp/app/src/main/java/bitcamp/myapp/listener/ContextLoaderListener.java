package bitcamp.myapp.listener;

import bitcamp.context.ApplicationContext;
import bitcamp.util.DBConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
  // 웹 어플리케이션이 사용할 자원을 준비시키고 해제시키는 역할

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("웹 어플리케이션 자원 준비");
    // DB con, DAO, txManager
    DBConnectionPool connectionPool = new DBConnectionPool(
        //              "jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb", "study", "Bitcamp!@#123"
        "jdbc:mysql://127.0.0.1/studydb", "study", "Bitcamp!@#123");

    Map<String, Object> beanMap = new HashMap<>();
    beanMap.put("connectionPool", connectionPool);

    try {
      // 공유 객체를 보관할 ApplicationContext 객체 준비
      ApplicationContext ctx = new ApplicationContext(beanMap, "bitcamp.myapp.dao");

      // 서블릿에서 사용할 수 있도록 웹 어플리케이션 저장소에 보관
      ServletContext 저장소 = sce.getServletContext();
      저장소.setAttribute("applicationContext", ctx);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
