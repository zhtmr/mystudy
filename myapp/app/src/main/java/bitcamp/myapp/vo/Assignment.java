package bitcamp.myapp.vo;


import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

public class Assignment implements Serializable/*, CsvString */ {

  @Serial
  private static final long serialVersionUID = 100L;
  private String title;
  private String content;
  private Date deadline;

  // 팩토리 메소드
  public static Assignment fromCsvString(String csv) {
    String[] values = csv.split(",");
    Assignment assignment = new Assignment();
    assignment.setTitle(values[0]);
    assignment.setContent(values[1]);
    assignment.setDeadline(Date.valueOf(values[2]));
    return assignment;
  }

  //  @Override
  //  public String toCsvString() {
  //    return String.format("%s,%s,%s", title, content, deadline);
  //  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  @Override
  public String toString() {
    return "Assignment{" + "title='" + title + '\'' + ", content='" + content + '\'' + ", deadline=" + deadline + '}';
  }
}
