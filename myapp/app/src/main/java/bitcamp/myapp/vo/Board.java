package bitcamp.myapp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Board implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no;
  private String title;
  private String content;
  private Member writer;
  private Date createdDate;
  private List<AttachedFile> files;
  private int fileCount;
  private int category;

}
