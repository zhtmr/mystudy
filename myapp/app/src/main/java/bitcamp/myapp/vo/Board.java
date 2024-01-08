package bitcamp.myapp.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Board implements Serializable/*, CsvString */ {

  @Serial
  private static final long serialVersionUID = 100L;
  private String title;
  private String content;
  private String writer;
  private Date createdDate;


  // 팩토리 메소드
  public static Board fromCsvString(String csv) {
    String[] values = csv.split(",");
    Board board = new Board();
    board.setTitle(values[0]);
    board.setContent(values[1]);
    board.setWriter(values[2]);
    board.setCreatedDate(new Date(Long.parseLong(values[3])));
    return board;
  }

  //  @Override
  //  public String toCsvString() {
  //    return String.format("%s,%s,%s,%d", title, content, writer, createdDate.getTime());
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

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
