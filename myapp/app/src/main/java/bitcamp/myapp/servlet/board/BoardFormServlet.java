package bitcamp.myapp.servlet.board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/form")
public class BoardFormServlet extends HttpServlet {


  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int category = Integer.parseInt(req.getParameter("category"));
    String title = category == 1 ? "게시글" : "가입인사";

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>부트캠프 5기</title>");
    out.println("</head>");
    out.println("<body>");
    out.printf("<h1>%s</h1>\n", title);

    out.printf("<form action='/board/add?category=%d'>\n", category);
    out.printf("<input name='category' type='hidden' value='%d'>\n", category);
    out.println("<div>");
    out.println("제목: <input type='text' name='title'>");
    out.println("</div>");
    out.println("<div>");
    out.println("<label for='content'>내용: </label><textarea name='content' id='content'></textarea>");
    out.println("</div>");
    if (category == 1) {
      out.println("<div>");
      out.println("첨부파일: <input name='files' type='file' multiple >");
      out.println("</div>");
    }
    out.println("<div>");
    out.println("<button>등록</button>");
    out.println("</div>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }
}
