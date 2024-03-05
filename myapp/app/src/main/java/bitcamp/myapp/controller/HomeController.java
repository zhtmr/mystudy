package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController {

  @RequestMapping
  public String execute(HttpServletRequest req, HttpServletResponse res)
      throws Exception {

    return "/home.jsp";
  }
}
