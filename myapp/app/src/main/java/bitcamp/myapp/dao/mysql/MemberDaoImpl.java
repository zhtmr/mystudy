package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Component;
import bitcamp.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemberDaoImpl implements MemberDao {

  DBConnectionPool threadConnection;

  public MemberDaoImpl(DBConnectionPool threadConnection) {
    this.threadConnection = threadConnection;
  }

  @Override
  public void add(Member member) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(
            "insert into members(email, name, password, photo) values (?, ?, sha2(?, 256),?)")) {
      pstmt.setString(1, member.getEmail());
      pstmt.setString(2, member.getName());
      pstmt.setString(3, member.getPassword());
      pstmt.setString(4, member.getPhoto());

      pstmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException("데이터 입력 오류", e);
    }
  }

  @Override
  public int delete(int no) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement("delete from members where member_no=?")) {
      pstmt.setInt(1, no);
      return pstmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException("데이터 삭제 오류", e);
    }
  }

  @Override
  public List<Member> findAll() {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement("select * from members")) {

      ArrayList<Member> list;
      try (ResultSet rs = pstmt.executeQuery()) {
        list = new ArrayList<>();
        while (rs.next()) {
          Member member = new Member();
          member.setNo(rs.getInt("member_no"));
          member.setEmail(rs.getString("email"));
          member.setName(rs.getString("name"));
          member.setPhoto(rs.getString("photo"));
          member.setCreatedDate(rs.getDate("created_date"));
          list.add(member);
        }
      }
      return list;
    } catch (Exception e) {
      throw new DaoException("데이터 조회 오류", e);
    }
  }

  @Override
  public Member findBy(int no) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement("select * from members where member_no=?")) {
      pstmt.setInt(1, no);
      try (ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
          Member member = new Member();
          member.setNo(rs.getInt("member_no"));
          member.setName(rs.getString("name"));
          member.setEmail(rs.getString("email"));
          member.setPhoto(rs.getString("photo"));
          member.setCreatedDate(rs.getDate("created_date"));
          return member;
        }
      }
      return null;
    } catch (Exception e) {
      throw new DaoException("데이터 조회 오류", e);
    }
  }

  @Override
  public int update(Member member) {
    String sql;
    if (member.getPassword().isEmpty()) {
      sql = "update members set email=?, name=?, photo=? where member_no=?";
    } else {
      sql = "update members set email=?, name=?, photo=?, password=sha2(?, 256) where member_no=?";
    }

    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {
      pstmt.setString(1, member.getEmail());
      pstmt.setString(2, member.getName());
      pstmt.setString(3, member.getPhoto());
      if (member.getPassword().isEmpty()) {
        pstmt.setInt(4, member.getNo());
      } else {
        pstmt.setString(4, member.getPassword());
        pstmt.setInt(5, member.getNo());
      }
      return pstmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException("데이터 수정 오류", e);
    }
  }

  @Override
  public Member findByEmailAndPassword(String email, String password) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(
            "select member_no, name, email, created_date from members where email=? and password=sha2(?, 256)")) {

      pstmt.setString(1, email);
      pstmt.setString(2, password);
      try (ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
          Member member = new Member();
          member.setNo(rs.getInt("member_no"));
          member.setName(rs.getString("name"));
          member.setEmail(rs.getString("email"));
          member.setCreatedDate(rs.getDate("created_date"));
          return member;
        }
        return null;
      }
    } catch (Exception e) {
      throw new DaoException("데이터 조회 오류", e);
    }
  }
}
