package bitcamp.myapp.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import java.beans.PropertyEditorSupport;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;

@ControllerAdvice
public class GlobalControllerAdvice {

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {

    webDataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(Date.valueOf(text));
      }
    });
  }

  @ExceptionHandler
  public ModelAndView exceptionHandler(Exception e) {
    ModelAndView mv = new ModelAndView();

    StringWriter writer = new StringWriter();
    PrintWriter out = new PrintWriter(writer);
    e.printStackTrace(out);

    mv.addObject("message", e.getMessage());
    mv.addObject("detail", writer.toString());
    mv.setViewName("/error.jsp");
    return mv;
  }
}
