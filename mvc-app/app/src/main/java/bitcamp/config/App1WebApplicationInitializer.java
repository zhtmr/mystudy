package bitcamp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

public class App1WebApplicationInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  private static Log log = LogFactory.getLog(App1WebApplicationInitializer.class);


  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {App1Config.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app1/*"};
  }

  @Override
  protected String getServletName() {
    return "app1";
  }

  @Override
  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
    registration.setMultipartConfig(
        new MultipartConfigElement(new File("./temp").getAbsolutePath(), 1024 * 1024 * 10, 1024 * 1024 * 100, 1024 * 1024 * 1));
  }
}
