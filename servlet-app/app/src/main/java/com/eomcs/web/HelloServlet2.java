package com.eomcs.web;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/hello2")
public class HelloServlet2 extends GenericServlet {


  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    System.out.println("Hello!");
  }



}
