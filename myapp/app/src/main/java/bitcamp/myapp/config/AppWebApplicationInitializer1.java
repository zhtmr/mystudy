package bitcamp.myapp.config;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.File;
import java.util.EnumSet;

public class AppWebApplicationInitializer1 /* implements WebApplicationInitializer */{
//  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    /* root ioc container 준비, app ioc container 준비, dispatcherservleet  */
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(RootConfig.class);
    rootContext.setServletContext(servletContext);
    rootContext.refresh();
    servletContext.addListener(new ContextLoaderListener(rootContext));

    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
    appContext.register(AppConfig.class);
    appContext.setParent(rootContext);
    appContext.setServletContext(servletContext);
    appContext.refresh();

    ServletRegistration.Dynamic registration =
        servletContext.addServlet("app", new DispatcherServlet(appContext));
    registration.addMapping("/app/*");
    registration.setLoadOnStartup(1);
    registration.setMultipartConfig(
        new MultipartConfigElement(new File("./temp").getAbsolutePath(), 1024 * 1024 * 10,
            1024 * 1024 * 100, 1024 * 1024 * 1));

    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8");
    FilterRegistration.Dynamic encodingFilter =
        servletContext.addFilter("CharacterEncodingFilter", characterEncodingFilter);
    encodingFilter.addMappingForServletNames(
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false,
        "app");

  }

}
