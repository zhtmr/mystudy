package bitcamp.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

@PropertySource({
    "classpath:config/ncp-secret.properties",
    "classpath:config/ncp.properties"
})
@EnableTransactionManagement
@SpringBootApplication
@Controller
public class App {

  public static void main(String[] args) throws Exception {
    System.out.println("과제관리 시스템 서버 실행!");
    SpringApplication.run(App.class, args);
  }

  @GetMapping("/home")
  public void home() {
  }

  @GetMapping("/about")
  public void about() {
  }

}
