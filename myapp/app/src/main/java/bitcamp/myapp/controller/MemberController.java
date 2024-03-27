package bitcamp.myapp.controller;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.service.impl.NcpStorageService;
import bitcamp.myapp.vo.Member;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController implements InitializingBean {
  private static final Log log = LogFactory.getLog(MemberController.class);

  private final MemberService memberService;
  private final NcpStorageService ncpStorageService;
  private String uploadDir;

  @Value("${ncp.ss.bucketname}")
  private String bucketName;

  @Override
  public void afterPropertiesSet() throws Exception {
    this.uploadDir = "member/";
    log.debug(String.format("bucketName: %s", bucketName));
    log.debug(String.format("uploadDir: %s", uploadDir));
  }

  @GetMapping("form")
  public void form() throws Exception {
//    return "/member/form.jsp";
  }

  @PostMapping("add")
  public String add(Member member, MultipartFile file) throws Exception {

    if (file.getSize() > 0) {
      String filename = ncpStorageService.upload(bucketName, uploadDir, file);
      member.setPhoto(filename);
    }
    memberService.add(member);
    return "redirect:list";
  }

  @GetMapping("list")
  public void list(Model model) throws Exception {
    model.addAttribute("list", memberService.list());
//    return "/member/list.jsp";
  }

  @PostMapping("update")
  public String update(MultipartFile file, Member member) throws Exception {

    Member old = memberService.get(member.getNo());
    if (old == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    member.setCreatedDate(old.getCreatedDate());

    if (file.getSize() > 0) {
      String filename = ncpStorageService.upload(bucketName, uploadDir, file);
      member.setPhoto(filename);
//      file.transferTo(new File(this.uploadDir + "/" + filename));
//      new File(uploadDir + "/" + old.getPhoto()).delete();
    } else {
      member.setPhoto(old.getPhoto());
    }
    memberService.update(member);
    return "redirect:list";
  }

  @GetMapping("view")
  public void view(int no, Model model) throws Exception {
    Member member = memberService.get(no);
    if (member == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }
    model.addAttribute("member", member);
//    return "/member/view.jsp";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    Member member = memberService.get(no);
    if (member == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    memberService.delete(no);
    String filename = member.getPhoto();
    if (filename != null) {
      new File(uploadDir + "/" + filename).delete();
    }
    return "redirect:list";
  }
}
