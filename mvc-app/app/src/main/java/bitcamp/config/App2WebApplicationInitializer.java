package bitcamp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class App2WebApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  private static Log log = LogFactory.getLog(App2WebApplicationInitializer.class);


  @Override
  protected WebApplicationContext createRootApplicationContext() {
    return null;  // admin 쪽에서 이미 만들었으니 안만든다.
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null; // admin 쪽에서 이미 만들었으니 안만든다.
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {App2Config.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app2/*"};
  }

  @Override
  protected String getServletName() {
    return "app2";
  }
}
