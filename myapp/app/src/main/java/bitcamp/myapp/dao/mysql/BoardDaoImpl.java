package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
  DBConnectionPool threadConnection;

  public BoardDaoImpl(DBConnectionPool threadConnection) {
    this.threadConnection = threadConnection;
  }

  @Override
  public void add(Board board) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(
            "insert into boards(title, content, writer, category) values (?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {
      pstmt.setString(1, board.getTitle());
      pstmt.setString(2, board.getContent());
      pstmt.setInt(3, board.getWriter().getNo());
      pstmt.setInt(4, board.getCategory());

      pstmt.executeUpdate();

      // fk 세팅
      try (ResultSet keyRs = pstmt.getGeneratedKeys()) {
        keyRs.next();
        board.setNo(keyRs.getInt(1));
      }

    } catch (Exception e) {
      throw new DaoException("데이터 입력 오류", e);
    }
  }

  @Override
  public int delete(int no) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement("delete from boards where board_no=?")) {
      pstmt.setInt(1, no);
      return pstmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException("데이터 삭제 오류", e);
    }
  }


  @Override
  public List<Board> findAll(int category) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement("""
             select
                      b.board_no,
                      b.title,
                      b.content,
                      b.created_date,
                      count(file_no) file_count,
                      m.member_no,
                      m.name
                   from boards b
                   left outer join board_files bf on b.board_no = bf.board_no
                   inner join members m on b.writer = m.member_no
                   where b.category=?
                   group by b.board_no
                   order by board_no desc
            """)) {

      pstmt.setInt(1, category);
      try (ResultSet rs = pstmt.executeQuery()) {
        ArrayList<Board> list = new ArrayList<>();

        while (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_no"));
          board.setTitle(rs.getString("title"));
          board.setContent(rs.getString("content"));
          board.setCreatedDate(rs.getDate("created_date"));
          board.setFileCount(rs.getInt("file_count"));
          board.setWriter(new Member()
              .name(rs.getString("name"))
              .no(rs.getInt("member_no")));
          list.add(board);
        }
        return list;
      }
    } catch (Exception e) {
      throw new DaoException("데이터 가져오기 오류", e);
    }
  }

  @Override
  public Board findBy(int no) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement("""
            select 
              b.board_no,
              b.title,
              b.content,
              m.member_no,
              m.name,
              b.created_date
            from boards b
            inner join members m on b.writer = member_no
            where board_no =?
            """)) {
      pstmt.setInt(1, no);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_no"));
          board.setTitle(rs.getString("title"));
          board.setContent(rs.getString("content"));
          board.setWriter(new Member()
              .name(rs.getString("name"))
              .no(rs.getInt("member_no")));
          board.setCreatedDate(rs.getDate("created_date"));

          return board;
        }
      }
      return null;
    } catch (Exception e) {
      throw new DaoException("데이터 가져오기 오류", e);
    }
  }

  @Override
  public int update(Board board) {
    try (Connection con = threadConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(
            "update boards set title=?, content=? where board_no=?")) {
      pstmt.setString(1, board.getTitle());
      pstmt.setString(2, board.getContent());
      pstmt.setInt(3, board.getNo());

      return pstmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException("데이터 변경 오류", e);
    }
  }
}
