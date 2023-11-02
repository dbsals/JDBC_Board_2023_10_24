package com.ym.jdbc;

import com.ym.jdbc.Rq;
import com.ym.jdbc.container.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

        Container.conn = conn;

        action(rq);

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

  private void action(Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      Container.articleController.doWrite();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      Container.articleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      Container.articleController.showDetail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      Container.articleController.doModify(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      Container.articleController.doDelete(rq);
    } else if (rq.equals("/usr/member/join")) {
      Container.memberController.join();
    } else if (rq.getUrlPath().equals("/usr/member/login")) {
      Container.memberController.login();
    } else if (rq.getUrlPath().equals("/usr/member/whoami")) {
      Container.memberController.whoami();
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    }
  }
}