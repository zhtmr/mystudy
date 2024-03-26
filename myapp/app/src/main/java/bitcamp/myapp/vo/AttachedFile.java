package bitcamp.myapp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor  // 빌더에서 사용함
@NoArgsConstructor  // mybatis 가 사용함
public class AttachedFile {
  private int no;
  private String filePath;
  private int boardNo;

}
