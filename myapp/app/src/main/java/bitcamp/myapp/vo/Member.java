package bitcamp.myapp.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable, CsvString {

  @Serial
  private static final long serialVersionUID = 100L;
  private String email;
  private String name;
  private String password;
  private Date createdDate;


  // 팩토리 메소드
  public static Member fromCsvString(String csv) {
    String[] values = csv.split(",");
    Member member = new Member();
    member.setEmail(values[0]);
    member.setName(values[1]);
    member.setPassword(values[2]);
    member.setCreatedDate(new Date(Long.parseLong(values[3])));
    return member;
  }

  @Override
  public String toCsvString() {
    return String.format("%s,%s,%s,%d", email, name, password, createdDate.getTime());
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
