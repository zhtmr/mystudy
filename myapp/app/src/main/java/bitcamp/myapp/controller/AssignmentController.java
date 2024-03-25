package bitcamp.myapp.controller;

import bitcamp.myapp.service.AssignmentService;
import bitcamp.myapp.vo.Assignment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {
  private static final Log log = LogFactory.getLog(AssignmentController.class);
  private final AssignmentService assignmentService;


  @GetMapping("form")
  public void form() throws Exception {
//    return "/assignment/form.jsp";
  }

  @PostMapping("add")
  public String add(Assignment assignment) throws Exception {
    assignmentService.add(assignment);
    return "redirect:list";
  }

  @GetMapping("list")
  public void list(Model map) throws Exception {
    map.addAttribute("list", assignmentService.list());
//    return "/assignment/list.jsp";
  }

  @GetMapping("view")
  public void view(int no, Model model) throws Exception {
    Assignment assignment = assignmentService.get(no);
    log.debug(assignment.toString());
    if (assignment == null) {
      throw new Exception("과제 번호가 유효하지 않습니다.");
    }

    model.addAttribute("assignment", assignment);
//    return "/assignment/view.jsp";
  }

  @PostMapping("update")
  public String update(Assignment assignment) throws Exception {
    Assignment old = this.assignmentService.get(assignment.getNo());
    if (old == null) {
      throw new Exception("과제 번호가 유효하지 않습니다.");
    }

    assignmentService.update(assignment);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    Assignment assignment = assignmentService.get(no);
    if (assignment == null) {
      throw new Exception("과제 번호가 유효하지 않습니다.");
    }
    assignmentService.delete(no);
    return "redirect:list";
  }
}
