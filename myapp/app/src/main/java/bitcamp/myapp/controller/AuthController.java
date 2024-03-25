package bitcamp.myapp.controller;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
  private final Log log = LogFactory.getLog(this.getClass());

  MemberService memberService;

  public AuthController(MemberService memberService) {
    log.debug("AuthController 생성");
    this.memberService = memberService;
  }

  @GetMapping("/form")
  public void form(@CookieValue(value = "email", required = false) String email, Model model) {
    model.addAttribute("email", email);
    // return void 로 할 경우 url 경로가 jsp 경로가 된다. (GET 요청일 경우에만 가능한 방법)
  }

  @PostMapping("/login")
  public String login(
      String email,
      String password,
      String saveEmail,
      HttpServletResponse response,
      HttpSession session) throws Exception {

    Cookie cookie;
    if (saveEmail != null) {
      cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 7);
      response.addCookie(cookie);
    } else {
      cookie = new Cookie("email", "");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Member member = memberService.get(email, password);
    if (member != null) {
      session.setAttribute("loginUser", member);
    }
    return "auth/login";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/index.html";
  }
}
