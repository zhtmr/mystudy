package bitcamp.myapp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(value={
    "bitcamp.util",
    "bitcamp.myapp.dao"
})
@PropertySource({
    "classpath:config/jdbc.properties"
})
public class RootConfig {
  private final Log log = LogFactory.getLog(this.getClass());

  public RootConfig() {
    log.debug("RootConfig() 호출됨");
  }
}
