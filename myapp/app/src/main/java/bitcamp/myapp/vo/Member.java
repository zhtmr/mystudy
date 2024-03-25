package bitcamp.myapp.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class Member implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no;
  private String email;
  private String name;
  private String password;
  private String photo;
  private Date createdDate;

}
