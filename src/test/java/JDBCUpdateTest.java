import java.sql.*;

public class JDBCUpdateTest {
  public static void main(String[] args) {
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";

    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String username = "ym";
    String password = "aa48aa76";

    Connection conn = null;
    PreparedStatement pstat = null;

    String sql = "UPDATE article";
    sql += " SET updateDate = NOW()";
    sql += ", title = '수정제목2'";
    sql += ", `content` = '수정내용2'";
    sql += " WHERE id = 2;";

    System.out.println("sql : " + sql);

    try {
      Class.forName(jdbcDriver);

      conn = DriverManager.getConnection(url, username, password);

      pstat = conn.prepareStatement(sql);

      pstat.executeUpdate();

    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");
    } catch (SQLException e) {
      System.out.println("에러 : " + e);
    } finally {
      try {
        if(pstat != null && !pstat.isClosed()) {
          pstat.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}