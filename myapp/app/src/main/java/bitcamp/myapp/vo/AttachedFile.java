package bitcamp.myapp.vo;

import lombok.*;

@Data
@Builder
public class AttachedFile {
  private int no;
  private String filePath;
  private int boardNo;


}
