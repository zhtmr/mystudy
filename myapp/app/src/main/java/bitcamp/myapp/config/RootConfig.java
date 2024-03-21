package bitcamp.myapp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.InputStream;

@ComponentScan(value = {"bitcamp.util", "bitcamp.myapp.dao"})
@PropertySource({"classpath:config/jdbc.properties"})
public class RootConfig {
  private final Log log = LogFactory.getLog(this.getClass());

  public RootConfig() {
    log.debug("RootConfig() 호출됨");
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    String resource = "config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    return new SqlSessionFactoryBuilder().build(inputStream);
  }
}
