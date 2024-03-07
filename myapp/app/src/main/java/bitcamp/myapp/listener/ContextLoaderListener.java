package bitcamp.myapp.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
  // 웹 어플리케이션이 사용할 자원을 준비시키고 해제시키는 역할

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("웹 어플리케이션 자원 준비");
    try {
      // 공유 객체를 보관할 ApplicationContext 객체 준비
      ApplicationContext ctx = new ClassPathXmlApplicationContext("config/application-context.xml");

      // 서블릿에서 사용할 수 있도록 웹 어플리케이션 저장소에 보관
      ServletContext 저장소 = sce.getServletContext();
      저장소.setAttribute("applicationContext", ctx);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
