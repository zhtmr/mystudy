package bitcamp.myapp.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.*;
import java.io.File;

public class AppWebApplicationInitializer3 /* extends AbstractDispatcherServletInitializer */{
  ServletContext servletContext;
  AnnotationConfigWebApplicationContext rootContext;

//  @Override
  protected WebApplicationContext createRootApplicationContext() {
    rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(RootConfig.class);
    rootContext.refresh();
    return rootContext;
  }


//  @Override
  protected WebApplicationContext createServletApplicationContext() {
    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
    appContext.register(AppConfig.class);
    appContext.setParent(rootContext);
    appContext.setServletContext(servletContext);
    appContext.refresh();
    return appContext;
  }

//  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }

//  @Override
  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
    registration.setMultipartConfig(
        new MultipartConfigElement(new File("./temp").getAbsolutePath(), 1024 * 1024 * 10,
            1024 * 1024 * 100, 1024 * 1024 * 1));
  }

//  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {new CharacterEncodingFilter("UTF-8")};
  }

//  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    this.servletContext = servletContext;
//    super.onStartup(servletContext);
  }
}
