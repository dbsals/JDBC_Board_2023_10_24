package com.ym.jdbc;

import com.ym.jdbc.container.Container;
import com.ym.jdbc.util.DBUtil;
import com.ym.jdbc.util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

  public void run() {
    Scanner sc = Container.scanner;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      Connection conn = null;

      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.out.println("예외 : MySQL 드라이버 클래스가 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try {
        conn = DriverManager.getConnection(url, "ym", "aa48aa76");

        doAction(conn, sc, cmd, rq);

      } catch (SQLException e) {
        System.out.println("에러 : " + e);
      } finally {
        try {
          if (conn != null && !conn.isClosed()) {
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    sc.close();
  }

  private void doAction(Connection conn, Scanner sc, String cmd, Rq rq) {
    PreparedStatement pstat = null;

    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 등록 ==");

      System.out.printf("제목 : ");
      String title = sc.nextLine();

      System.out.printf("내용 : ");
      String content = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `content` = ?", content);

      int id = DBUtil.insert(conn, sql);

      System.out.printf("%d번 게시물을 작성하였습니다.\n", id);

    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      ResultSet rs = null;

      List<Article> articles = new ArrayList<>();

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articlesListMap = DBUtil.selectRows(conn, sql);

      for (Map<String, Object> articleMap : articlesListMap) {
        articles.add(new Article(articleMap));
      }

      System.out.println("== 게시물 리스트 ==");

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      System.out.println("번호 / 제목");

      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      int id = rq.getIntParam("id", 0);

      System.out.println("== 게시물 상세보기 ==");

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

      if (articleMap.isEmpty()) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      Article article = new Article(articleMap);

      System.out.printf("번호 : %d\n", article.id);
      System.out.printf("작성날짜 : %s\n", article.regDate);
      System.out.printf("제목 : %s\n", article.title);
      System.out.printf("내용 : %s\n", article.content);


    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      System.out.println("== 게시물 수정 ==");

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articleCount = DBUtil.selectRowIntValue(conn, sql);

      if (articleCount == 0) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String content = sc.nextLine();

      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("set updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `content` = ?", content);
      sql.append("WHERE id = ?", id);

      DBUtil.update(conn, sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      int id = rq.getIntParam("id", 0);

      System.out.println("== 게시물 삭제 ==");

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articleCount = DBUtil.selectRowIntValue(conn, sql);

      if (articleCount == 0) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      sql = new SecSql();
      sql.append("DELETE FROM article");
      sql.append(" WHERE id = ?", id);

      DBUtil.delete(conn, sql);

      System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
    }
    if (rq.getUrlPath().equals("/usr/member/join")) {
      String loginId;
      String loginPw;
      String loginPwConfirm;
      String name;

      System.out.println("== 회원 가입 ==");

      while (true) {
        System.out.printf("로그인 아이디 : ");
        loginId = sc.nextLine().trim();

        if(loginId.length() == 0) {
          System.out.println("로그인 아이디를 입력해주세요.");
          continue;
        }

        break;
      }

      while (true) {
        System.out.printf("로그인 비번 : ");
        loginPw = sc.nextLine().trim();

        if(loginPw.length() == 0) {
          System.out.println("로그인 비번을 입력해주세요.");
          continue;
        }

        boolean loginPwConfirmIsSame = true;

        while (true) {
          System.out.printf("로그인 비번확인 : ");
          loginPwConfirm = sc.nextLine().trim();

          if(loginPwConfirm.length() == 0) {
            System.out.println("로그인 비번확인을 입력해주세요.");
            continue;
          }

          if(loginPw.equals(loginPwConfirm) == false) {
            System.out.println("로그인 비번이 일치하지 않습니다. 다시 입력해주세요.");
            loginPwConfirmIsSame = false;
            break;
          }

          break;
        }

        if(loginPwConfirmIsSame) {
          break;
        }
      }

      while (true) {
        System.out.printf("이름 : ");
        name = sc.nextLine().trim();

        if(name.length() == 0) {
          System.out.println("이름를 입력해주세요.");
          continue;
        }

        break;
      }

      SecSql sql = new SecSql();
      sql.append("INSERT INTO `member`");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", loginId = ?", loginId);
      sql.append(", loginPw = ?", loginPw);
      sql.append(", name = ?", name);

      DBUtil.insert(conn, sql);

      System.out.printf("\"%s\"님 회원 가입을 환영합니다.\n", name);
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    }
  }
}