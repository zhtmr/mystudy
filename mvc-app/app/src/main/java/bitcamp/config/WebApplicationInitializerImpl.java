package bitcamp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializerImpl extends AbstractContextLoaderInitializer {

  private static Log log = LogFactory.getLog(WebApplicationInitializerImpl.class);
  AnnotationConfigWebApplicationContext rootContext;

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    log.debug("WebApplicationInitializerImpl.onStartup() 호출됨");

    // super 클래스의 구현 내용은 그대로 유지해야 한다.
    // super 클래스의 메소드에서 ContextLoaderListener 객체를 만들기 때문이다.
    super.onStartup(servletContext);

    // app IoC 추가 후 DispatcherServlet 이 사용할 IoC 컨테이너로 설정
    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
    appContext.setParent(rootContext);
    appContext.register(AppConfig.class);
    appContext.refresh();
    ServletRegistration.Dynamic appServletRegi =
        servletContext.addServlet("app", new DispatcherServlet(appContext));

    appServletRegi.addMapping("/app/*");
    appServletRegi.setLoadOnStartup(1);

    // admin IoC 추가 후 DispatcherServlet 이 사용할 IoC 컨테이너로 설정
    AnnotationConfigWebApplicationContext adminContext =
        new AnnotationConfigWebApplicationContext();
    adminContext.setParent(rootContext);
    adminContext.register(AdminConfig.class);
    adminContext.refresh();
    ServletRegistration.Dynamic adminServletRegi =
        servletContext.addServlet("admin", new DispatcherServlet(adminContext));

    adminServletRegi.addMapping("/admin/*");
    adminServletRegi.setLoadOnStartup(1);
  }

  @Override
  protected WebApplicationContext createRootApplicationContext() {
    // parent IoC
    rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(RootConfig.class);
    rootContext.refresh();
    return rootContext;
  }
}
