package bitcamp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializerImpl implements WebApplicationInitializer {

  private static Log log = LogFactory.getLog(WebApplicationInitializerImpl.class);

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    log.debug("WebApplicationInitializerImpl.onStartup() 호출됨");
    // parent IoC
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(RootConfig.class);
    rootContext.refresh();

    // listener 추가
    ContextLoaderListener listener = new ContextLoaderListener(rootContext);
    servletContext.addListener(listener);

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
    AnnotationConfigWebApplicationContext adminContext = new AnnotationConfigWebApplicationContext();
    adminContext.setParent(rootContext);
    adminContext.register(AdminConfig.class);
    adminContext.refresh();
    ServletRegistration.Dynamic adminServletRegi =
        servletContext.addServlet("admin", new DispatcherServlet(adminContext));

    adminServletRegi.addMapping("/admin/*");
    adminServletRegi.setLoadOnStartup(1);
  }
}
