package bitcamp.myapp.controller;


import org.springframework.stereotype.Component;

@Component
public class AboutController {

  public AboutController() {
    System.out.println("AboutController 생성");
  }

  @RequestMapping("/about")
  public String about()
      throws Exception {
    return "/about.jsp";
  }
}
